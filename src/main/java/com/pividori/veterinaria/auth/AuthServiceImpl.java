package com.pividori.veterinaria.auth;

import com.pividori.veterinaria.dtos.LoginRequest;
import com.pividori.veterinaria.dtos.RegisterRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.mappers.UserMapper;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.models.utility.RoleEnum;
import com.pividori.veterinaria.repositories.RoleRepository;
import com.pividori.veterinaria.repositories.UserRepository;
import com.pividori.veterinaria.security.CustomUserDetails;
import com.pividori.veterinaria.security.JwtService;
import com.pividori.veterinaria.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailService;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        Role defaultRole = roleRepository.findByRoleEnum(RoleEnum.CLIENT).
                orElseThrow(() -> new IllegalStateException("Default role not found"));

        User newUser = User.builder()
                .name(registerRequest.name())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .dob(registerRequest.dob())
                .role(defaultRole)
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialNonExpired(true)
                .build();

        userRepository.save(newUser);

        String token = jwtService.generateToken(new CustomUserDetails(newUser));
        String refreshToken = jwtService.generateRefreshToken(new CustomUserDetails(newUser));

        return new AuthResponse(token, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password())
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        String token = jwtService.generateToken(customUserDetails);

        UserResponse userResponse = UserMapper.toResponse(user);

        return new AuthResponse(token, " bearer", userResponse);
    }

    public AuthResponse refreshToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalStateException("Invalid Authorization header");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new IllegalStateException("Invalid refresh token");
        }

        // cargar usuario
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        // validar refresh token
        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new IllegalStateException("Expired or invalid refresh token");
        }

        // generar nuevo access token
        String newAccessToken = jwtService.generateToken(userDetails);

        // devolver ambos tokens
        return new AuthResponse(newAccessToken, refreshToken);
    }
}

package com.pividori.veterinaria.services;

import com.pividori.veterinaria.dtos.LoginRequest;
import com.pividori.veterinaria.dtos.RegisterRequest;
import com.pividori.veterinaria.dtos.TokenResponse;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.models.utility.RoleEnum;
import com.pividori.veterinaria.repositorys.RoleRepository;
import com.pividori.veterinaria.repositorys.UserRepository;
import com.pividori.veterinaria.securitys.CustomerUserDetails;
import com.pividori.veterinaria.securitys.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public TokenResponse register(RegisterRequest registerRequest) {
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

        String token = jwtService.generateToken(new CustomerUserDetails(newUser));
        String refreshToken = jwtService.generateRefreshToken(new CustomerUserDetails(newUser));

        return new TokenResponse(token, refreshToken);
    }

    public TokenResponse login(LoginRequest loginRequest) {

        var authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
        );

        authenticationManager.authenticate(authToken);

        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.username());

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(String authHeader) {

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
        return new TokenResponse(newAccessToken, refreshToken);
    }
}

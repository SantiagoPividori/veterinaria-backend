package com.pividori.veterinaria.auth;

import com.pividori.veterinaria.shared.exceptions.InvalidRefreshTokenException;
import com.pividori.veterinaria.shared.exceptions.UserNotFoundException;
import com.pividori.veterinaria.clinic.user.infrastructure.in.mappers.UserMapper;
import com.pividori.veterinaria.clinic.user.domain.User;
import com.pividori.veterinaria.clinic.user.infrastructure.out.UserRepository;
import com.pividori.veterinaria.shared.security.CustomUserDetails;
import com.pividori.veterinaria.shared.security.JwtService;
import com.pividori.veterinaria.shared.security.SecurityConstants;
import com.pividori.veterinaria.clinic.user.application.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {

        User user = userServiceImpl.register(registerRequest);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        String accessToken = jwtService.generateAccessToken(customUserDetails);
        String refreshToken = jwtService.generateRefreshToken(customUserDetails);

        Instant now = Instant.now();
        user.setRefreshTokenJti(jwtService.extractJti(refreshToken));
        user.setRefreshTokenExpirationAt(now.plus(jwtService.getRefreshTokenExpirationInMs(), ChronoUnit.MILLIS));
        userRepository.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                SecurityConstants.TOKEN_TYPE_BEARER,
                now.plus(jwtService.getTokenExpirationInMs(), ChronoUnit.MILLIS),
                user.getRefreshTokenExpirationAt(),
                UserMapper.toResponse(user));
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password())
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        String accessToken = jwtService.generateAccessToken(customUserDetails);
        String refreshToken = jwtService.generateRefreshToken(customUserDetails);

        Instant now = Instant.now();
        user.setRefreshTokenJti(jwtService.extractJti(refreshToken));
        user.setRefreshTokenExpirationAt(now.plus(jwtService.getRefreshTokenExpirationInMs(), ChronoUnit.MILLIS));
        userRepository.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                SecurityConstants.TOKEN_TYPE_BEARER,
                now.plus(jwtService.getTokenExpirationInMs(), ChronoUnit.MILLIS),
                user.getRefreshTokenExpirationAt(),
                UserMapper.toResponse(user));
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();
        final String username = jwtService.extractUsername(refreshToken);
        String jti = jwtService.extractJti(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("username", username));

        if (user.getRefreshTokenExpirationAt() == null || user.getRefreshTokenExpirationAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException();
        }

        // validar refresh token
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        if (!jti.equals(user.getRefreshTokenJti())) {
            throw new InvalidRefreshTokenException();
        }


        // 4) Generar nuevo access token
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String newAccessToken = jwtService.generateAccessToken(customUserDetails);

        // 5)
        Instant now = Instant.now();
        String newRefreshToken = jwtService.generateRefreshToken(customUserDetails);
        user.setRefreshTokenJti(jwtService.extractJti(newRefreshToken));
        user.setRefreshTokenExpirationAt(now.plus(jwtService.getRefreshTokenExpirationInMs(), ChronoUnit.MILLIS));
        userRepository.save(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                SecurityConstants.TOKEN_TYPE_BEARER,
                now.plus(jwtService.getTokenExpirationInMs(), ChronoUnit.MILLIS),
                user.getRefreshTokenExpirationAt(),
                UserMapper.toResponse(user)
        );
    }
}

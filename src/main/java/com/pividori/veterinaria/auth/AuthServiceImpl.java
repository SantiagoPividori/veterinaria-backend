package com.pividori.veterinaria.auth;

import com.pividori.veterinaria.exceptions.InvalidRefreshTokenException;
import com.pividori.veterinaria.mappers.UserMapper;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.repositories.UserRepository;
import com.pividori.veterinaria.security.CustomUserDetails;
import com.pividori.veterinaria.security.JwtService;
import com.pividori.veterinaria.security.SecurityConstants;
import com.pividori.veterinaria.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${security.jwt.refresh.expiration}")
    private Long refreshTokenDuration;

    public AuthResponse register(RegisterRequest registerRequest) {

        User user = userServiceImpl.register(registerRequest);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        String accessToken = jwtService.generateAccessToken(customUserDetails);
        String refreshToken = jwtService.generateRefreshToken(customUserDetails);

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiration(Instant.now().plus(refreshTokenDuration, ChronoUnit.MILLIS));
        userRepository.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                SecurityConstants.TOKEN_TYPE_BEARER,
                jwtService.getAccessTokenExpirationInSeconds(),
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

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiration(Instant.now().plus(refreshTokenDuration, ChronoUnit.MILLIS));
        userRepository.save(user);

        return new AuthResponse(
                accessToken,
                refreshToken,
                SecurityConstants.TOKEN_TYPE_BEARER,
                jwtService.getAccessTokenExpirationInSeconds(),
                UserMapper.toResponse(user));
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();

        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (user.getRefreshTokenExpiration() == null || user.getRefreshTokenExpiration().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException();
        }

        // validar refresh token
        if (!jwtService.isTokenValid(refreshToken, new CustomUserDetails(user))) {
            throw new InvalidRefreshTokenException();
        }

        // 4) Generar nuevo access token
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String newAccessToken = jwtService.generateAccessToken(customUserDetails);

        // 5)
        String newRefreshToken = jwtService.generateRefreshToken(customUserDetails);
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer",
                jwtService.getAccessTokenExpirationInSeconds(),
                UserMapper.toResponse(user)
        );
    }
}

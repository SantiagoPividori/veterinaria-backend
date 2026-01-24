package com.pividori.veterinaria.saas.iam.application.service;

import com.pividori.veterinaria.saas.iam.infrastructure.port.in.dtos.AuthResponse;
import com.pividori.veterinaria.saas.iam.infrastructure.port.in.dtos.LoginRequest;
import com.pividori.veterinaria.saas.iam.infrastructure.port.in.dtos.RefreshTokenRequest;
import com.pividori.veterinaria.onboarding.infrastructure.dto.RegisterRequest;
import com.pividori.veterinaria.shared.exceptions.InvalidRefreshTokenException;
import com.pividori.veterinaria.shared.exceptions.UserNotFoundException;
import com.pividori.veterinaria.saas.iam.infrastructure.port.in.mappers.UserWebMapper;
import com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity.UserEntity;
import com.pividori.veterinaria.saas.iam.infrastructure.port.out.UserRepository;
import com.pividori.veterinaria.shared.security.CustomUserDetails;
import com.pividori.veterinaria.shared.security.JwtService;
import com.pividori.veterinaria.shared.security.SecurityConstants;
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

    private final UserService userServiceImpl;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {

        UserEntity user = userServiceImpl.register(registerRequest);
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
                UserWebMapper.toResponse(user));
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password())
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = customUserDetails.getUser();

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
                UserWebMapper.toResponse(user));
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();
        final String username = jwtService.extractUsername(refreshToken);
        String jti = jwtService.extractJti(refreshToken);

        UserEntity user = userRepository.findByUsername(username)
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
                UserWebMapper.toResponse(user)
        );
    }
}

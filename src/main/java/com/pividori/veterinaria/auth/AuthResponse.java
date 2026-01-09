package com.pividori.veterinaria.auth;

import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.UserResponse;

import java.time.Instant;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Instant expiresAt,
        Instant  refreshExpiresAt,
        UserResponse userResponse
) {
}
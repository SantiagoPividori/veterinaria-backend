package com.pividori.veterinaria.identity.infrastructure.port.in.dtos;

import java.time.Instant;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Instant expiresAt,
        Instant  refreshExpiresAt
) {
}
package com.pividori.veterinaria.onboarding.infrastructure.dto;

import java.time.Instant;

public record RegisterResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Instant expiresAt,
        Instant  refreshExpiresAt,
        Long userId,
        Long clinicId
) {
}

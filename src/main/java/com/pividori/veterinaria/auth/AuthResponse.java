package com.pividori.veterinaria.auth;

import com.pividori.veterinaria.dtos.UserResponse;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long refreshTokenExpiration,
        UserResponse userResponse
) {
}
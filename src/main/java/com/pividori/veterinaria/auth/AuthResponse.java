package com.pividori.veterinaria.auth;

import com.pividori.veterinaria.dtos.UserResponse;

public record AuthResponse(
        String accessToken,
        String tokenType,
        UserResponse userResponse
) {
}
package com.pividori.veterinaria.dtos;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String lastname,
        String username,
        String email,
        LocalDate dob,
        String role
) {
}

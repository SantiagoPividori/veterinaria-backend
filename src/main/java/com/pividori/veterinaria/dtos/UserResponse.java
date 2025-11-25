package com.pividori.veterinaria.dtos;

import com.pividori.veterinaria.models.Role;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String lastname,
        String username,
        String email,
        LocalDate dob,
        Role role
) {
}

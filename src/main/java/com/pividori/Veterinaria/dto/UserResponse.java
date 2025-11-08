package com.pividori.Veterinaria.dto;

import com.pividori.Veterinaria.model.utility.Role;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String lastname,
        String email,
        LocalDate dob,
        Role role
) {
}

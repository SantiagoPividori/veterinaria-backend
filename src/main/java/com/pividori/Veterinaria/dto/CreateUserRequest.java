package com.pividori.Veterinaria.dto;

import com.pividori.Veterinaria.model.utility.Role;

import java.time.LocalDate;

public record CreateUserRequest(
        String name,
        String lastname,
        String email,
        String password,
        LocalDate dob,
        Role role
) {
}

package com.pividori.veterinaria.dtos;

import java.time.LocalDate;

public record CreateUserRequest(
        String name,
        String lastname,
        String username,
        String email,
        String password,
        LocalDate dob
) {
}

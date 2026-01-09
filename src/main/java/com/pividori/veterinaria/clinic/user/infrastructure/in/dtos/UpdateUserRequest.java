package com.pividori.veterinaria.clinic.user.infrastructure.in.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UpdateUserRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Lastname is required")
        String lastname,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotNull(message = "Date of birth is required")
        @Past
        LocalDate dob
) {
}

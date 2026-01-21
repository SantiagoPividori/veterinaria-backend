package com.pividori.veterinaria.onboarding.infrastructure.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank(message = "Name of clinic is required")
        String nameClinic,
        @NotBlank(message = "Time zone is required")
        String timeZone,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Lastname is required")
        String lastname,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#._-])[A-Za-z\\d@$!%*?&#._-]{8,64}$",
                message = "Password must be 8-64 characters long and include at least one lowercase letter, one uppercase letter, one digit and one special character"
        )
        String password,
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotNull(message = "Date of birth is required")
        @Past
        LocalDate dob
) {
}

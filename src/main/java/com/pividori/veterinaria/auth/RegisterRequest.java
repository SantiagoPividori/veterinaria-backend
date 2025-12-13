package com.pividori.veterinaria.auth;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Lastname is required")
        String lastname,
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be 3-20 characters long")
        String username,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#._-])[A-Za-z\\d@$!%*?&#._-]{8,64}$",
                message = "Password must be 8-64 characters long and include at least one lowercase letter, one uppercase letter, one digit and one special character"
        )
        String password,
        @NotNull(message = "Date of birth is required")
        @Past
        LocalDate dob
) {
}

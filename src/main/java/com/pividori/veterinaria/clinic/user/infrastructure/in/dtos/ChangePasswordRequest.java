package com.pividori.veterinaria.clinic.user.infrastructure.in.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotBlank(message = "Current password is required")
        String currentPassword,
        @NotBlank(message = "New password is required")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#._-])[A-Za-z\\d@$!%*?&#._-]{8,64}$",
                message = "New password must be 8-64 characters long and include at least one lowercase letter, one uppercase letter, one digit and one special character"
        )
        String newPassword
) {
}

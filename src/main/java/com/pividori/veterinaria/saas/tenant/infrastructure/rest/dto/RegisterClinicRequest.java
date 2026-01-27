package com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RegisterClinicRequest(
        /*   Usuario   */
        @NotBlank(message = "Email for user ir required")
        @Email(message = "Invalid email format")
        String ownerEmail,
        @NotBlank(message = "Password is required")
        String ownerPassword,
        @NotBlank(message = "First name is required")
        String ownerFirstName,
        @NotBlank(message = "Last name is required")
        String ownerLastName,
        @NotBlank(message = "Birth date is required")
        @Past(message = "The birth date must be in the past") // Validación de Bean Validation
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate ownerBirthDate,
        /*   Clinica   */
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Legal name is required")
        String legalName,
        @NotBlank(message = "CUIT is required")
        String cuit,
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        String phoneNumber,
        String street,
        String city,
        String province,
        String postalCode,
        @NotBlank(message = "Type plan is required")
        String planType,
        @NotBlank(message = "Time zone is required")
        String timeZone
) {
}
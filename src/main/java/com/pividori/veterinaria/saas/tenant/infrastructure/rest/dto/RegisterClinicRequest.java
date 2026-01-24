package com.pividori.veterinaria.saas.tenant.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterClinicRequest(
        /*   Usuario   */


        /*   Clinica   */
        @NotBlank(message = "Name ir required")
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
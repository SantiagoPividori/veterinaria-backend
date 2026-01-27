package com.pividori.veterinaria.saas.tenant.application.port.in.command;

import java.time.LocalDate;

public record RegisterClinicCommand(
        /*   Usuario   */
        String ownerEmail,
        String ownerPassword,
        String ownerFirstName,
        String ownerLastName,
        LocalDate ownerBirthDate,
        /*   Clinica   */
        String name,
        String legalName,
        String cuit,
        String email,
        String phoneNumber,
        String street,
        String city,
        String province,
        String postalCode,
        String planType,
        String timeZone
) {
}

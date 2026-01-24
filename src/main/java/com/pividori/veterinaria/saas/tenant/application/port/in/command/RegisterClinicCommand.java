package com.pividori.veterinaria.saas.tenant.application.port.in.command;

public record RegisterClinicCommand(
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

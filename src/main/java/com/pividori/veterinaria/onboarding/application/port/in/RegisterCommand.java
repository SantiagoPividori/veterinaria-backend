package com.pividori.veterinaria.onboarding.application.port.in;

import java.time.LocalDate;

public record RegisterCommand(
        String clinicName,
        String timeZone,
        String name,
        String lastname,
        String email,
        String password,
        String phoneNumber,
        LocalDate dob
) {
}

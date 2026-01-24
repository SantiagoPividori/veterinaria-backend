package com.pividori.veterinaria.saas.iam.application.port.in;

import java.time.LocalDate;

public record RegisterUserCommand(
        String name,
        String lastname,
        String email,
        String password,
        String phoneNumber,
        LocalDate dob
) {
}

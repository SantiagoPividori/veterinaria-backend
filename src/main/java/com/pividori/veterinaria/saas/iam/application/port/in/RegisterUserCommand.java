package com.pividori.veterinaria.saas.iam.application.port.in;

import java.time.LocalDate;

public record RegisterUserCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate dob
) {
}

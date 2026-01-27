package com.pividori.veterinaria.saas.tenant.application.port.out;

import com.pividori.veterinaria.shared.UserId;

import java.time.LocalDate;

public interface RegisterUserPort {

    UserId registerUser(String email, String password, String firstName, String lastName, LocalDate birthDate);
}

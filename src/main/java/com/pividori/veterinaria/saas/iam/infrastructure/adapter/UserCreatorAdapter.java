package com.pividori.veterinaria.saas.iam.infrastructure.adapter;

import com.pividori.veterinaria.saas.iam.application.port.in.RegisterUserCommand;
import com.pividori.veterinaria.saas.iam.application.port.in.RegisterUserUseCase;
import com.pividori.veterinaria.saas.tenant.application.port.out.RegisterUserPort;
import com.pividori.veterinaria.shared.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserCreatorAdapter implements RegisterUserPort {

    private final RegisterUserUseCase registerUserUseCase;

    @Override
    public UserId registerUser(String email, String password, String firstName, String lastName, LocalDate birthDate) {

        registerUserUseCase.execute(new RegisterUserCommand(
                email,
                password,
                firstName,
                lastName,
                birthDate
        ));


        return user.getId();
    }
}

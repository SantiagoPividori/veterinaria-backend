package com.pividori.veterinaria.saas.iam.application.service;

import com.pividori.veterinaria.saas.iam.application.port.in.RegisterUserCommand;
import com.pividori.veterinaria.saas.iam.application.port.in.RegisterUserResult;
import com.pividori.veterinaria.saas.iam.application.port.in.RegisterUserUseCase;
import com.pividori.veterinaria.saas.iam.application.port.out.UserRepositoryPort;
import com.pividori.veterinaria.saas.iam.domain.enums.UserRole;
import com.pividori.veterinaria.saas.iam.domain.model.User;
import com.pividori.veterinaria.saas.iam.domain.valueobject.BirthDate;
import com.pividori.veterinaria.saas.iam.domain.valueobject.UserEmail;
import com.pividori.veterinaria.saas.iam.domain.valueobject.UserName;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public RegisterUserResult execute(RegisterUserCommand registerUserCommand) {

        User user = User.create(
                new UserEmail(registerUserCommand.email()),
                registerUserCommand.password(),
                new UserName(registerUserCommand.firstName()),
                new UserName(registerUserCommand.lastName()),
                new BirthDate(registerUserCommand.dob()),
                UserRole.ADMIN
                );

        User userSaved = userRepositoryPort.save(user);

        return new RegisterUserResult(userSaved.getId());
    }
}

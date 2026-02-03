package com.pividori.veterinaria.saas.iam.application.port.in;

public interface RegisterUserUseCase {
    RegisterUserResult execute(RegisterUserCommand registerUserCommand);
}

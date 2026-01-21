package com.pividori.veterinaria.identity.application.port.in;

public interface RegisterUserUseCase {
    RegisterUserResult register(RegisterUserCommand registerUserCommand);
}

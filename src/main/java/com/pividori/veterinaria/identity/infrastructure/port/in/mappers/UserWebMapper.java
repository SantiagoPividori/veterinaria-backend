package com.pividori.veterinaria.identity.infrastructure.port.in.mappers;

import com.pividori.veterinaria.identity.application.port.in.RegisterUserCommand;

public final class UserWebMapper {

    private UserWebMapper() {
    }

    public static RegisterUserResponse toRegisterUserResponse(RegisterUserResult registerUserResult) {
        return new RegisterUserResponse(registerUserResult.userId().value());
    }

    public static RegisterUserCommand toRegisterUserCommand(RegisterUserRequest registerUserRequest) {
        return new RegisterUserCommand(registerUserRequest.name(), registerUserRequest.lastname(), registerUserRequest.phoneNumber());
    }
}

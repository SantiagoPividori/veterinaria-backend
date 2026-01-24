package com.pividori.veterinaria.saas.iam.infrastructure.port.in.mappers;

import com.pividori.veterinaria.saas.iam.application.port.in.RegisterUserCommand;

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

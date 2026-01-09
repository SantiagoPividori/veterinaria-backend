package com.pividori.veterinaria.clinic.user.infrastructure.in.mappers;

import com.pividori.veterinaria.auth.RegisterRequest;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.UserResponse;
import com.pividori.veterinaria.clinic.user.domain.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                user.getDob(),
                user.getRole().getRoleEnum().name());
    }

    public static User fromRegisterRequest(RegisterRequest registerRequest) {
        return User.builder()
                .name(registerRequest.name())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .dob(registerRequest.dob())
                .build();
    }
}

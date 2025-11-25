package com.pividori.veterinaria.mappers;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                user.getDob(),
                user.getRole());
    }

    public static User toEntity(CreateUserRequest createUserRequest, Role defaultRole, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(createUserRequest.name())
                .lastname(createUserRequest.lastname())
                .username(createUserRequest.username())
                .email(createUserRequest.email())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .dob(createUserRequest.dob())
                .role(defaultRole)
                .build();
    }

}

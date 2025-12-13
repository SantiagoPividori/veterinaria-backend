package com.pividori.veterinaria.mappers;

import com.pividori.veterinaria.auth.RegisterRequest;
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

    public static User toEntity(RegisterRequest registerRequest, Role defaultRole, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(registerRequest.name())
                .lastname(registerRequest.lastname())
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .dob(registerRequest.dob())
                .role(defaultRole)
                .build();
    }
}

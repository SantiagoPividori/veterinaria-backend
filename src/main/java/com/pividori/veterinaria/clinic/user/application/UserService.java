package com.pividori.veterinaria.clinic.user.application;

import com.pividori.veterinaria.auth.RegisterRequest;
import com.pividori.veterinaria.clinic.user.domain.User;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.ChangePasswordRequest;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.UpdateUserRequest;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    List<UserResponse> findAll();

    void deleteById(Long id);

    UserResponse update(Long id, UpdateUserRequest updateUserRequest);

    UserResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest);

    User register(RegisterRequest registerRequest);

}

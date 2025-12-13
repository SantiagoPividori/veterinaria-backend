package com.pividori.veterinaria.services;

import com.pividori.veterinaria.auth.RegisterRequest;
import com.pividori.veterinaria.dtos.*;
import com.pividori.veterinaria.models.User;

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

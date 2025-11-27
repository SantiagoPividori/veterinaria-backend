package com.pividori.veterinaria.services;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.LoginRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.models.User;

import java.util.List;

public interface UserService {

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    UserResponse register(CreateUserRequest createdUserRequest);

}

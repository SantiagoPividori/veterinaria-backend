package com.pividori.veterinaria.services;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.LoginRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.models.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    UserResponse registerUser(CreateUserRequest createdUserRequest);

}

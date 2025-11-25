package com.pividori.veterinaria.services;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.models.User;

import java.util.List;

public interface UserService {

    public User findUserByUsername(String username);

    public UserResponse registerUser(CreateUserRequest createdUserRequest);
}

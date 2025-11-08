package com.pividori.Veterinaria.service;

import com.pividori.Veterinaria.dto.CreateUserRequest;
import com.pividori.Veterinaria.dto.UserResponse;

public interface IUserService {

    public UserResponse createUser(CreateUserRequest createdUserRequest);

    public UserResponse getUserById(Long id);

    public void deletedUserById(Long id);

    public UserResponse editUser(Long id);

}

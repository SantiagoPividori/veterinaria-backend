package com.pividori.Veterinaria.service;

import com.pividori.Veterinaria.dto.CreateUserRequest;
import com.pividori.Veterinaria.dto.UserResponse;
import com.pividori.Veterinaria.exception.UserNotFoundException;
import com.pividori.Veterinaria.model.User;
import com.pividori.Veterinaria.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest createdUserRequest) {
        User savedUser = userRepository.save(new User(
                createdUserRequest.name(),
                createdUserRequest.lastname(),
                createdUserRequest.email(),
                createdUserRequest.password(), //WARNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                createdUserRequest.dob(),
                createdUserRequest.role()
        ));

        return new UserResponse(
                savedUser.getId(), savedUser.getName(), savedUser.getLastname(), savedUser.getEmail(), savedUser.getDob(), savedUser.getRole()
        );
    }

    @Override
    public UserResponse getUserById(Long id) {

        User foundUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return new UserResponse(
                foundUser.getId(),
                foundUser.getName(),
                foundUser.getLastname(),
                foundUser.getEmail(),
                foundUser.getDob(),
                foundUser.getRole()
        );
    }

    @Override
    public void deletedUserById(Long id) {

    }

    @Override
    public UserResponse editUser(Long id) {
        return null;
    }
}

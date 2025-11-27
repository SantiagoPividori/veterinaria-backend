package com.pividori.veterinaria.services;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.exceptions.EmailAlreadyTakenException;
import com.pividori.veterinaria.exceptions.UserNotFoundException;
import com.pividori.veterinaria.exceptions.UsernameAlreadyTakenException;
import com.pividori.veterinaria.mappers.UserMapper;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserResponse findByUsername(String username) {

        User foundUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User {} not found", username);
                    return new UserNotFoundException("username", username);
                });

        return UserMapper.toResponse(foundUser);

    }

    @Override
    public UserResponse findByEmail(String email) {

        User foundUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Email {} is not found", email);
                    return new UserNotFoundException("email", email);
                });

        return UserMapper.toResponse(foundUser);
    }

    private User findUserEntityByIdOrThrow(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Id {} is not found", id);
                    return new UserNotFoundException("id", id.toString());
                });
    }

    @Override
    public UserResponse register(CreateUserRequest createdUserRequest) {

        //Verificaciones.
        if (userRepository.existsByUsername(createdUserRequest.username())) {
            log.warn("Username {} is already taken", createdUserRequest.username());
            throw new UsernameAlreadyTakenException(createdUserRequest.username());
        }

        if (userRepository.existsByEmail(createdUserRequest.email())) {
            log.warn("Email {} is already taken", createdUserRequest.email());
            throw new EmailAlreadyTakenException(createdUserRequest.email());
        }

        Role defaultRole = roleService.getDefaultClientRole();

        User newUser = UserMapper.toEntity(createdUserRequest, defaultRole, passwordEncoder);

        initializeSecurityFlags(newUser);

        User savedUser = userRepository.save(newUser);
        log.info("Created new user {}", savedUser.getUsername());

        return UserMapper.toResponse(savedUser);

    }

    private void initializeSecurityFlags(User newUser) {
        newUser.setEnabled(true);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialNonExpired(true);
    }

}

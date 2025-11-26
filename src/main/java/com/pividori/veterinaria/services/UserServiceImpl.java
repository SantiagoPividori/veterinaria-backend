package com.pividori.veterinaria.services;

import com.pividori.veterinaria.dtos.CreateUserRequest;
import com.pividori.veterinaria.dtos.LoginRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.exceptions.EmailAlreadyTakenException;
import com.pividori.veterinaria.exceptions.PaswordIncorrectException;
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
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("ERROR"));
    }

    @Override
    public UserResponse registerUser(CreateUserRequest createdUserRequest) {

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

        //Pasamos la referencia en memoria de la variable, por eso se modifica y no hace falta devolver nada.
        initializeSecurityFlags(newUser);

        User savedUser = userRepository.save(newUser);
        log.info("Creating new user {}", savedUser.getUsername());

        return UserMapper.toResponse(savedUser);

    }


    private void initializeSecurityFlags(User newUser) {
        newUser.setEnabled(true);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialNonExpired(true);
    }

}

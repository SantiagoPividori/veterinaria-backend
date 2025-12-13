package com.pividori.veterinaria.services;

import com.pividori.veterinaria.auth.RegisterRequest;
import com.pividori.veterinaria.dtos.*;
import com.pividori.veterinaria.exceptions.*;
import com.pividori.veterinaria.mappers.UserMapper;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

//ToDo: #Añadir validaciónes.

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

    @Override
    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {

        User deleteUser = findUserEntityByIdOrThrow(id);

        userRepository.delete(deleteUser);
        log.info("Deleted user with id: {}", id);
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest updateUserRequest) {

        User foundUser = findUserEntityByIdOrThrow(id);

        if (StringUtils.hasText(updateUserRequest.name()) && !foundUser.getName().equals(updateUserRequest.name())) {
            foundUser.setName(updateUserRequest.name());
        }

        if (StringUtils.hasText(updateUserRequest.lastname()) && !foundUser.getLastname().equals(updateUserRequest.lastname())) {
            foundUser.setLastname(updateUserRequest.lastname());
        }

        if (StringUtils.hasText(updateUserRequest.email()) && !foundUser.getEmail().equals(updateUserRequest.email())) {
            if (userRepository.existsByEmail(updateUserRequest.email())) {
                log.warn("Email {} is already taken", updateUserRequest.email());
                throw new EmailAlreadyTakenException(updateUserRequest.email());
            }
            foundUser.setEmail(updateUserRequest.email());
        }

        if (updateUserRequest.dob() != null && !foundUser.getDob().isEqual(updateUserRequest.dob())) {
            foundUser.setDob(updateUserRequest.dob());
        }

        User savedUser = userRepository.save(foundUser);

        return UserMapper.toResponse(savedUser);
    }

    //ToDo: Verificar largo/fuerza del password.
    @Override
    public UserResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) {

        User foundUser = findUserEntityByIdOrThrow(id);

        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), foundUser.getPassword())) {
            throw new PasswordIncorrectException(foundUser.getUsername());
        }

        if (changePasswordRequest.currentPassword().equals(changePasswordRequest.newPassword())) {
            throw new PasswordEqualsException();
        }

        foundUser.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));

        userRepository.save(foundUser);

        log.info("Password changed successfully for user with id {}", id);

        return UserMapper.toResponse(foundUser);
    }

    @Override
    public User register(RegisterRequest registerRequest) {

        //Verificaciones.
        if (userRepository.existsByUsername(registerRequest.username())) {
            log.warn("Username {} is already taken", registerRequest.username());
            throw new UsernameAlreadyTakenException(registerRequest.username());
        }

        if (userRepository.existsByEmail(registerRequest.email())) {
            log.warn("Email {} is already taken", registerRequest.email());
            throw new EmailAlreadyTakenException(registerRequest.email());
        }

        Role defaultRole = roleService.getDefaultClientRole();

        User newUser = UserMapper.toEntity(registerRequest, defaultRole, passwordEncoder);

        initializeSecurityFlags(newUser);

        User savedUser = userRepository.save(newUser);
        log.info("Created new user {}", savedUser.getUsername());

        return savedUser;

    }

    private User findUserEntityByIdOrThrow(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Id {} is not found", id);
                    return new UserNotFoundException("id", id.toString());
                });
    }

    private void initializeSecurityFlags(User newUser) {
        newUser.setEnabled(true);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialNonExpired(true);
    }

}

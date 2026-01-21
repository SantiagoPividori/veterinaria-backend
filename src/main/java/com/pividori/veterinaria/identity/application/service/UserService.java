package com.pividori.veterinaria.identity.application.service;

import com.pividori.veterinaria.onboarding.infrastructure.dto.RegisterRequest;
import com.pividori.veterinaria.identity.infrastructure.port.in.mappers.UserWebMapper;
import com.pividori.veterinaria.identity.infrastructure.persistence.UserEntity;
import com.pividori.veterinaria.identity.infrastructure.port.out.UserRepository;
import com.pividori.veterinaria.shared.exceptions.*;
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
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse findByUsername(String username) {

        UserEntity foundUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User {} not found", username);
                    return new UserNotFoundException("username", username);
                });

        return UserWebMapper.toResponse(foundUser);

    }

    public UserResponse findByEmail(String email) {

        UserEntity foundUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Email {} is not found", email);
                    return new UserNotFoundException("email", email);
                });

        return UserWebMapper.toResponse(foundUser);
    }

    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(UserWebMapper::toResponse)
                .toList();
    }

    public void deleteById(Long id) {

        UserEntity deleteUser = findUserEntityByIdOrThrow(id);

        userRepository.delete(deleteUser);
        log.info("Deleted user with value: {}", id);
    }

    public UserResponse update(Long id, UpdateUserRequest updateUserRequest) {

        UserEntity foundUser = findUserEntityByIdOrThrow(id);

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

        UserEntity savedUser = userRepository.save(foundUser);

        return UserWebMapper.toResponse(savedUser);
    }

    //ToDo: Verificar largo/fuerza del password.
    public UserResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) {

        UserEntity foundUser = findUserEntityByIdOrThrow(id);

        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), foundUser.getPassword())) {
            throw new PasswordIncorrectException(foundUser.getUsername());
        }

        if (changePasswordRequest.currentPassword().equals(changePasswordRequest.newPassword())) {
            throw new PasswordEqualsException();
        }

        foundUser.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));

        userRepository.save(foundUser);

        log.info("Password changed successfully for user with value {}", id);

        return UserWebMapper.toResponse(foundUser);
    }

    public UserEntity register(RegisterRequest registerRequest) {

        //Verificaciones.
        if (userRepository.existsByUsername(registerRequest.username())) {
            log.warn("Username {} is already taken", registerRequest.username());
            throw new UsernameAlreadyTakenException(registerRequest.username());
        }

        if (userRepository.existsByEmail(registerRequest.email())) {
            log.warn("Email {} is already taken", registerRequest.email());
            throw new EmailAlreadyTakenException(registerRequest.email());
        }

        //Creamos user y guardamos
        Role defaultRole = roleService.getDefaultClientRole();
        UserEntity newUser = UserWebMapper.fromRegisterRequest(registerRequest);
        newUser.setRole(defaultRole);
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        initializeSecurityFlags(newUser);

        UserEntity savedUser = userRepository.save(newUser);
        log.info("Created new user {}", savedUser.getUsername());

        return savedUser;
    }

    private UserEntity findUserEntityByIdOrThrow(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Id {} is not found", id);
                    return new UserNotFoundException("id", id.toString());
                });
    }

    private void initializeSecurityFlags(UserEntity newUser) {
        newUser.setEnabled(true);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
    }

}

package com.pividori.veterinaria.clinic.user.application;

import com.pividori.veterinaria.auth.RegisterRequest;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.ChangePasswordRequest;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.UpdateUserRequest;
import com.pividori.veterinaria.clinic.user.infrastructure.in.dtos.UserResponse;
import com.pividori.veterinaria.clinic.user.infrastructure.in.mappers.UserMapper;
import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.services.RoleService;
import com.pividori.veterinaria.clinic.user.domain.User;
import com.pividori.veterinaria.clinic.user.infrastructure.out.UserRepository;
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
        log.info("Deleted user with value: {}", id);
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

        log.info("Password changed successfully for user with value {}", id);

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

        //Creamos user y guardamos
        Role defaultRole = roleService.getDefaultClientRole();
        User newUser = UserMapper.fromRegisterRequest(registerRequest);
        newUser.setRole(defaultRole);
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
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
        newUser.setCredentialsNonExpired(true);
    }

}

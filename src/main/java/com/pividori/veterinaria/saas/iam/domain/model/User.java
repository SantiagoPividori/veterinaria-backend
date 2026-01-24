package com.pividori.veterinaria.saas.iam.domain.model;

import com.pividori.veterinaria.saas.iam.domain.enums.UserRole;
import com.pividori.veterinaria.saas.iam.domain.valueobject.BirthDate;
import com.pividori.veterinaria.saas.iam.domain.valueobject.UserEmail;
import com.pividori.veterinaria.saas.iam.domain.valueobject.UserName;
import com.pividori.veterinaria.shared.UserId;
import com.pividori.veterinaria.shared.exceptions.DomainException;

import java.time.Instant;

public class User {

    private final UserId id;
    private final UserEmail email;
    private String password;
    private final UserName firstName;
    private final UserName lastName;
    private final BirthDate birthDate;
    private final UserRole role;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private User(UserId id, UserEmail email, String password, UserName firstName, UserName lastName,
                 BirthDate birthDate, UserRole role, boolean active, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.role = role;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(UserEmail email, String password, UserName firstName,
                              UserName lastName, BirthDate birthDate, UserRole role) {
        Instant now = Instant.now();
        return new User(
                UserId.generate(),
                email,
                password,
                firstName,
                lastName,
                birthDate,
                role,
                true,
                now,
                now
        );
    }

    public static User reconstitute(UserId id, UserEmail email, String password, UserName firstName, UserName lastName,
                                    BirthDate birthDate, UserRole role, boolean active, Instant createdAt, Instant updatedAt) {
        return new User(id,
                email,
                password,
                firstName,
                lastName,
                birthDate,
                role,
                active,
                createdAt,
                updatedAt
        );
    }

    public void updatePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new DomainException("New password cannot be null or empty");
        }
        this.password = newPassword;
    }

}

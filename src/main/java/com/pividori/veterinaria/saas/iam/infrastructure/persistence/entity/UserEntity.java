package com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, length = 100)
    @ToString.Exclude
    private String password;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dob;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "created_at", nullable = false,  updatable = false)
    private Instant createdAt;
    /* --- JWT and Security --- */
    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "refresh_token_jti", nullable = false)
    private String refreshTokenJti;
    @Column(name = "refresh_token_expiration_at", nullable = false)
    private Instant refreshTokenExpirationAt;
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;
    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;
    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    public UserEntity(String firstname, String lastname, String email, String password, LocalDate dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
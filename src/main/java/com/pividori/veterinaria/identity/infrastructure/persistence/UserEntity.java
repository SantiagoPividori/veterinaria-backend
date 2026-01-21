package com.pividori.veterinaria.identity.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, length = 100)
    @ToString.Exclude
    private String password;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dob;
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

    public UserEntity(String name, String lastname, String username, String email, String password, LocalDate dob) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
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
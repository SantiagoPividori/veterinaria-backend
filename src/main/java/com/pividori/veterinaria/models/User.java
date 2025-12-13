package com.pividori.veterinaria.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

//TODO :

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // With this annotation generated automatically.
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;
    @Column(nullable = false)
    @NotBlank(message = "Lastname is required")
    private String lastname;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters long")
    private String username;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#._-])[A-Za-z\\d@$!%*?&#._-]{8,64}$",
            message = "Password must be 8-64 characters long and include at least one lowercase letter, one uppercase letter, one digit and one special character"
    )
    private String password;
    @Column(name = "day_of_birth", nullable = false)
    @NotNull(message = "Date of birth is required")
    @Past
    private LocalDate dob;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @NotBlank(message = "Role is required")
    private Role role;
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
    @Column(name = "refresh_token_expiration", nullable = false)
    private Instant refreshTokenExpiration;
    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
    @Column(name = "account_non_Expired", nullable = false)
    private boolean accountNonExpired;
    @Column(name = "account_non_Locked", nullable = false)
    private boolean accountNonLocked;
    @Column(name = "credential_non_Expired", nullable = false)
    private boolean credentialNonExpired;

    public User(String name, String lastname, String username, String email, String password, LocalDate dob, Role role) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.role = role;
    }

}
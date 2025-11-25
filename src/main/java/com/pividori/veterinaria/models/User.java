package com.pividori.veterinaria.models;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "day_of_birth", nullable = false)
    private LocalDate dob;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
    @Column(name = "account_non_Expired", nullable = false)
    private boolean accountNonExpired;
    @Column(name = "account_non_Locked", nullable = false)
    private boolean accountNonLocked;
    @Column(name = "credential_non_Expired", nullable = false)
    private boolean credentialNonExpired;

    public User(String name, String lastname, String email, String password, LocalDate dob, Role role) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.role = role;
    }

}
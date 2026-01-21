package com.pividori.veterinaria.identity.domain;

import java.time.Instant;
import java.time.LocalDate;

public class User {

    private Long id;
    private final String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private LocalDate dob;
    private String refreshTokenJti;
    private Instant refreshTokenExpirationAt;
    private boolean isEnabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public User(String name, String lastname, String username, String email, String password, LocalDate dob) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

}

package com.pividori.Veterinaria.model;

import com.pividori.Veterinaria.model.utility.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // With this annotation generated automatically.
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String lastname, String email, String password, LocalDate dob, Role role) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.role = role;
    }

}

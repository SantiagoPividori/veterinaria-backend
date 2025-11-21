package com.pividori.Veterinaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//TODO : #PARA ROLES = Considerar utilizar FetchType.LAZY + @EntityGraph!

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
    @ManyToMany(fetch = FetchType.EAGER) //CONSIDERAR UTILIZAR FetchType.LAZY + @EntityGraph!
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
    @Column(name = "account_non_Expired", nullable = false)
    private boolean accountNonExpired;
    @Column(name = "account_non_Locked", nullable = false)
    private boolean accountNonLocked;
    @Column(name = "credential_non_Expired", nullable = false)
    private boolean credentialNonExpired;

    public User(String name, String lastname, String email, String password, LocalDate dob, Set<Role> roles) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.roles = roles;
        this.isEnabled = true;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialNonExpired = true;
    }

}
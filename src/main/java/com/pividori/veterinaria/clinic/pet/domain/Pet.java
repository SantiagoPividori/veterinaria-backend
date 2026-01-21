package com.pividori.veterinaria.clinic.pet.domain;

import com.pividori.veterinaria.identity.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "day_of_birth",nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String species;
    @Column(nullable = false)
    private String breed;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    public Pet(String name, LocalDate dob, String species, String breed, UserEntity owner) {
        this.name = name;
        this.dob = dob;
        this.species = species;
        this.breed = breed;
        this.owner = owner;
    }

}

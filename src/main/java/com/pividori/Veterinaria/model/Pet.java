package com.pividori.Veterinaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private LocalDate dob;
    private String species;
    private String breed;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Pet(String name, LocalDate dob, String species, String breed, User owner) {
        this.name = name;
        this.dob = dob;
        this.species = species;
        this.breed = breed;
        this.owner = owner;
    }

}

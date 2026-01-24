package com.pividori.veterinaria.clinic.pet.infrastructure.persistance;

import com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity.UserEntity;
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
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "day_of_birth",nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String specie;
    @Column(nullable = false)
    private String breed;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    public PetEntity(String name, LocalDate dob, String specie, String breed, UserEntity owner) {
        this.name = name;
        this.dob = dob;
        this.specie = specie;
        this.breed = breed;
        this.owner = owner;
    }

}

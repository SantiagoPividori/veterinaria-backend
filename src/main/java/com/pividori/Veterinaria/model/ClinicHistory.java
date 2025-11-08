package com.pividori.Veterinaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClinicHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private String diagnostic;
    private String treatment;
    private LocalDate dod;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private User veterinarian;

    public ClinicHistory(Pet pet, String diagnostic, String treatment, LocalDate dod, User veterinarian) {
        this.pet = pet;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.dod = dod;
        this.veterinarian = veterinarian;
    }

}

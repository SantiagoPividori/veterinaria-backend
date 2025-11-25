package com.pividori.veterinaria.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "clinic_histories")
public class ClinicHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @Column(nullable = false)
    private String diagnostic;
    @Column(nullable = false)
    private String treatment;
    @Column(name = "day_of_diagnostic", nullable = false)
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

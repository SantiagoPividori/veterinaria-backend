package com.pividori.veterinaria.clinic.appointment.domain;

import com.pividori.veterinaria.clinic.pet.domain.Pet;
import com.pividori.veterinaria.identity.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "day_of_appointment", nullable = false)
    private LocalDateTime dot;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private UserEntity veterinarian;
    @Column(nullable = false)
    private String reasonConsultation;
    @Enumerated(EnumType.STRING)
    @Column(name = "state_turn")
    private StateEnum stateTurn;

    public Appointment(LocalDateTime dot, Pet pet, UserEntity veterinarian, String reasonConsultation) {
        this.dot = dot;
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.reasonConsultation = reasonConsultation;
        this.stateTurn = StateEnum.RESERVED;
    }

    public Appointment(LocalDateTime dot, Pet pet, UserEntity veterinarian, String reasonConsultation, StateEnum stateTurn) {
        this.dot = dot;
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.reasonConsultation = reasonConsultation;
        this.stateTurn = stateTurn;
    }

}

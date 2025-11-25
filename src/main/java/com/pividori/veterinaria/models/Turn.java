package com.pividori.veterinaria.models;

import com.pividori.veterinaria.models.utility.StateEnum;
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
@Table(name = "turns")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "day_of_turn", nullable = false)
    private LocalDateTime dot;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private User veterinarian;
    @Column(nullable = false)
    private String reasonConsultation;
    @Enumerated(EnumType.STRING)
    @Column(name = "state_turn")
    private StateEnum stateTurn;

    public Turn(LocalDateTime dot, Pet pet, User veterinarian, String reasonConsultation) {
        this.dot = dot;
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.reasonConsultation = reasonConsultation;
        this.stateTurn = StateEnum.RESERVED;
    }

    public Turn(LocalDateTime dot, Pet pet, User veterinarian, String reasonConsultation, StateEnum stateTurn) {
        this.dot = dot;
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.reasonConsultation = reasonConsultation;
        this.stateTurn = stateTurn;
    }

}

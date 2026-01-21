package com.pividori.veterinaria.clinic.pet.infrastructure.port.in.dtos;

import java.time.LocalDate;

public record PetRequest(
        String name,
        LocalDate dob,
        String specie,
        String breed
) {
}

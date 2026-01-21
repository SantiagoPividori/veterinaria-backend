package com.pividori.veterinaria.clinic.pet.infrastructure.port.out;

import com.pividori.veterinaria.clinic.pet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}

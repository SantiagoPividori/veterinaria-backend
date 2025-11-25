package com.pividori.veterinaria.repositorys;

import com.pividori.veterinaria.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}

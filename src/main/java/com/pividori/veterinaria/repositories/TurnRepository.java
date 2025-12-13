package com.pividori.veterinaria.repositories;

import com.pividori.veterinaria.models.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
}
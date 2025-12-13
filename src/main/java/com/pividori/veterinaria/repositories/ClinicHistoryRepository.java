package com.pividori.veterinaria.repositories;

import com.pividori.veterinaria.models.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {
}
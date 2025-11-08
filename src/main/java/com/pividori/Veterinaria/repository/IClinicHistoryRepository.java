package com.pividori.Veterinaria.repository;

import com.pividori.Veterinaria.model.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {
}
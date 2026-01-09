package com.pividori.veterinaria.clinic.clinichistory.infrastructure.out;

import com.pividori.veterinaria.clinic.clinichistory.domain.ClinicHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicHistoryRepository extends JpaRepository<ClinicHistory, Long> {
}
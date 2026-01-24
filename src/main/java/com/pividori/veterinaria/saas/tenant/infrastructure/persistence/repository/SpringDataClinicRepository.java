package com.pividori.veterinaria.saas.tenant.infrastructure.persistence.repository;

import com.pividori.veterinaria.saas.tenant.infrastructure.persistence.entity.ClinicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataClinicRepository extends JpaRepository<ClinicEntity, Long> {
}

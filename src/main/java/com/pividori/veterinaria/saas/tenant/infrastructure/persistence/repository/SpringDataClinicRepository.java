package com.pividori.veterinaria.saas.tenant.infrastructure.persistence.repository;

import com.pividori.veterinaria.saas.tenant.infrastructure.persistence.entity.ClinicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataClinicRepository extends JpaRepository<ClinicEntity, UUID> {
}

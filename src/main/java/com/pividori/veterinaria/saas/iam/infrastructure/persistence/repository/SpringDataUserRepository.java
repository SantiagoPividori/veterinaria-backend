package com.pividori.veterinaria.saas.iam.infrastructure.persistence.repository;

import com.pividori.veterinaria.saas.iam.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {
}

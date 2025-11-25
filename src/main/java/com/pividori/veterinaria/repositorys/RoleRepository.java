package com.pividori.veterinaria.repositorys;

import com.pividori.veterinaria.models.Role;
import com.pividori.veterinaria.models.utility.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleEnum(RoleEnum roleEnum);

}

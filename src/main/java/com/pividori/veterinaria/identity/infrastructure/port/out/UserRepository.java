package com.pividori.veterinaria.identity.infrastructure.port.out;

import com.pividori.veterinaria.identity.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    /* This is the form of make a query in sql. Esta es la forma de hacer querys personalizadas.
    @Query("SELECT u FROM User u WHERE u.username = ?")
    Optional<User> findUser(String username);
    */
}

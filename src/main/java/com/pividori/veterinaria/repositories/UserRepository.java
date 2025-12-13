package com.pividori.veterinaria.repositories;

import com.pividori.veterinaria.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    /* This is the form of make a query in sql. Esta es la forma de hacer querys personalizadas.
    @Query("SELECT u FROM User u WHERE u.username = ?")
    Optional<User> findUser(String username);
    */
}

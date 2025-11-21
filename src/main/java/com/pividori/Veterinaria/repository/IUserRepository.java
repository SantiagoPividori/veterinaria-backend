package com.pividori.Veterinaria.repository;

import com.pividori.Veterinaria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    public User findUserByUsername(String username);

    /* This is the form of make a query in sql. Esta es la forma de hacer querys personalizadas.
    @Query("SELECT u FROM User u WHERE u.username = ?")
    Optional<User> findUser(String username);
    */
}

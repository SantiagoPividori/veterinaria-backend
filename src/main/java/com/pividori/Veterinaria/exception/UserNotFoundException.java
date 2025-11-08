package com.pividori.Veterinaria.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Don´t found user with this id: " + id);
    }

}

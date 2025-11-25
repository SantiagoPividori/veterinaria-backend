package com.pividori.veterinaria.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Don´t found user with this id: " + id);
    }

}

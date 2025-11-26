package com.pividori.veterinaria.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Don´t found user with this username: " + username);
    }

}

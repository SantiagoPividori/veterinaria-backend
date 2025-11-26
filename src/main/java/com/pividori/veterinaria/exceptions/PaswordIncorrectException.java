package com.pividori.veterinaria.exceptions;

public class PaswordIncorrectException extends RuntimeException {
    public PaswordIncorrectException(String username) {
        super("This user's password is incorrect: " + username);
    }
}

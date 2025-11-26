package com.pividori.veterinaria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Handler of all exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {

    //When something controller throw exception to UserNotFoundException type, this controller take control.
    @ExceptionHandler
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRoleNotFound(RoleNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUsernameAlreadyTaken(UsernameAlreadyTakenException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEmailAlreadyTaken(EmailAlreadyTakenException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handlePasswordIncorrect(PaswordIncorrectException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }


}

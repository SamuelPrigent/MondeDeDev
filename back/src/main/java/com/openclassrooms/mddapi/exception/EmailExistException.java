package com.openclassrooms.mddapi.exception;

public class EmailExistException extends RuntimeException {
    public EmailExistException() {
        super("Un compte existe déjà avec cet email");
    }
}
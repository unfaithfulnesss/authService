package com.example.authservice.exceptions;

public abstract class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}

package com.example.authservice.exceptions;

public class RefreshTokenRevokedException extends AuthException {
    public RefreshTokenRevokedException(String message) {
        super(message);
    }
}

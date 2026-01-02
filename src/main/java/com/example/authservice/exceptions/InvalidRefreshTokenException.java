package com.example.authservice.exceptions;

public class InvalidRefreshTokenException extends AuthException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}

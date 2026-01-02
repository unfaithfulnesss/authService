package com.example.authservice.exceptions;

public class RefreshTokenExpiredException extends AuthException{
    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}

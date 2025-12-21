package com.example.authservice.DTOs;

public record LoginRequest(
        String email,
        String password
) {
}

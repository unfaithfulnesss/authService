package com.example.authservice.DTOs;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
package com.example.authservice.DTOs;

import com.example.authservice.Enums.TokenType;

public record RefreshRequest(
        String refreshToken
) {}

package com.example.authservice.DTOs;

import java.time.Instant;

public record ErrorResponse(
        String code,
        String message,
        Instant timestamp
) {
}

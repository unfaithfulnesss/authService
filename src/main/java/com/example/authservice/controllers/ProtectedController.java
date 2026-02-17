package com.example.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/protected")
@RequiredArgsConstructor

public class ProtectedController {

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getName());
        return ResponseEntity.ok(
                Map.of(
                        "principal", authentication.getPrincipal(),
                        "username", authentication.getName(),
                        "authorities", authentication.getAuthorities()
                )
        );
    }
}

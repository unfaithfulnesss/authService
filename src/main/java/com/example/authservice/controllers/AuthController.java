package com.example.authservice.controllers;

import com.example.authservice.DTOs.AuthResponse;
import com.example.authservice.DTOs.LoginRequest;
import com.example.authservice.entities.User;
import com.example.authservice.repositories.UserRepository;
import com.example.authservice.security.services.JwtService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN ENDPOINT HIT");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // If we reach here, authentication succeeded
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow();


        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = "REFRESH_TOKEN_WILL_GO_HERE";

        return ResponseEntity.ok(
                new AuthResponse(accessToken, refreshToken)
        );
    }

}

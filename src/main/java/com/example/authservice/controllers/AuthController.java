package com.example.authservice.controllers;

import com.example.authservice.DTOs.AuthResponse;
import com.example.authservice.DTOs.LoginRequest;
import com.example.authservice.DTOs.RefreshRequest;
import com.example.authservice.entities.Token;
import com.example.authservice.entities.User;
import com.example.authservice.exceptions.InvalidCredentialsException;
import com.example.authservice.repositories.UserRepository;
import com.example.authservice.security.services.JwtService;
import com.example.authservice.services.RefreshTokenService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("LOGIN ENDPOINT HIT");
        try {
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

        Token refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new AuthResponse(accessToken, refreshToken.getToken())
        );
        } catch (BadCredentialsException e){
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {

        Token oldRefreshToken = refreshTokenService.verifyRefreshToken(request.refreshToken());

        Token newRefreshToken = refreshTokenService.rotateRefreshToken(oldRefreshToken);

        String newAccessToken = jwtService.generateAccessToken(newRefreshToken.getUser());

        return ResponseEntity.ok(
                new AuthResponse(newAccessToken, newRefreshToken.getToken())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest request) {

        Token refreshToken = refreshTokenService.verifyRefreshToken(request.refreshToken());

        refreshTokenService.revokeToken(refreshToken);

        return ResponseEntity.noContent().build();
    }

      //testing protected endpoint
//    @GetMapping("/me")
//    public ResponseEntity<?> me(Authentication authentication) {
//        Authentication auth =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        System.out.println(auth.getName());
//        return ResponseEntity.ok(
//                Map.of(
//                        "principal", authentication.getPrincipal(),
//                        "username", authentication.getName(),
//                        "authorities", authentication.getAuthorities()
//                )
//        );
//    }

}

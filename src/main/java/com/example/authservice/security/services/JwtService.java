package com.example.authservice.security.services;

import com.example.authservice.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
@Getter
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 min

    private final SecretKey secretKey;

    public JwtService() {
        this.secretKey = Keys.hmacShaKeyFor(
                Base64.getDecoder().decode("v5y1jvDORtVoeyMVDGJa2r+8kAO5jtwI+UxNFm2PFKc=")
        );
    }

    public String generateAccessToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


}


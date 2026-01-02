package com.example.authservice.services;

import com.example.authservice.Enums.TokenType;
import com.example.authservice.entities.Token;
import com.example.authservice.entities.User;
import com.example.authservice.exceptions.InvalidRefreshTokenException;
import com.example.authservice.exceptions.RefreshTokenExpiredException;
import com.example.authservice.exceptions.RefreshTokenRevokedException;
import com.example.authservice.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_DURATION_DAYS = 30;

    private final TokenRepository tokenRepository;

    public Token createRefreshToken(User user) {
        Token refreshToken = new Token();
        refreshToken.setUser(user);
        refreshToken.setTokenType(TokenType.REFRESH);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setIssuedAt(Instant.now());
        refreshToken.setExpiresAt(
                Instant.now().plus(REFRESH_TOKEN_DURATION_DAYS, ChronoUnit.DAYS)
        );
        refreshToken.setExpired(false);
        refreshToken.setRevoked(false);

        return tokenRepository.save(refreshToken);
    }

//    public Token verifyRefreshToken(String token) {
//
//        Token refreshToken = tokenRepository.findByToken(token)
//                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//
//        if (refreshToken.isRevoked()) {
//            throw new RuntimeException("Refresh token revoked");
//        }
//
//        if (!refreshToken.isExpired() && refreshToken.getExpiresAt().isBefore(Instant.now())) {
//            refreshToken.setExpired(true);
//            tokenRepository.save(refreshToken);
//            throw new RuntimeException("Refresh token expired");
//        }
//
//        return refreshToken;
//    }

    public Token verifyRefreshToken(String tokenValue) {

        Token token = tokenRepository.findByTokenAndTokenType(tokenValue, TokenType.REFRESH)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));

        if (token.isRevoked()) {
            // TOKEN REUSE DETECTED
            try {
                tokenRepository.revokeAllRefreshTokensForUser(token.getUser());
            } catch (Exception e){
                throw new RefreshTokenRevokedException("Refresh token reuse detected");
            }



        }


        if (token.getExpiresAt().isBefore(Instant.now())) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
            throw new RefreshTokenExpiredException("Refresh token has expired");
        }

        return token;
    }

    public Token rotateRefreshToken(Token oldToken) {

        // Revoke the old refresh token
        oldToken.setRevoked(true);
        tokenRepository.save(oldToken);

        // Create and return a new refresh token
        return createRefreshToken(oldToken.getUser());
    }

    public void revokeToken(Token token) {
        token.setRevoked(true);
        tokenRepository.save(token);
    }
}

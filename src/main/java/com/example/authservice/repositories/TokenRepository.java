package com.example.authservice.repositories;

import com.example.authservice.Enums.TokenType;
import com.example.authservice.entities.Token;
import com.example.authservice.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUserIdAndExpiredFalseAndRevokedFalse(Long userId);

    Optional<Token> findByTokenAndTokenType(String token, TokenType tokenType);

    List<Token> findAllByUserAndTokenType(User user, TokenType tokenType);

    void deleteAllByUserAndTokenType(User user, TokenType tokenType);

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("""
    update Token t
    set t.revoked = true
    where t.user = :user
      and t.tokenType = 'REFRESH'
      and t.revoked = false
    """)
    void revokeAllRefreshTokensForUser(User user);
}

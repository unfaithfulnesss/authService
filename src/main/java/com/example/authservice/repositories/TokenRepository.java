package com.example.authservice.repositories;

import com.example.authservice.entities.Token;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUserIdAndExpiredFalseAndRevokedFalse(Long userId);
}

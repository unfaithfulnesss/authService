package com.example.authservice.entities;

import com.example.authservice.Enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token; // opaque refresh token

    @Enumerated(EnumType.STRING)
    private TokenType tokenType; // REFRESH

    private boolean expired;
    private boolean revoked;

    private Instant issuedAt;
    private Instant expiresAt;

    private String deviceId;
    private String userAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

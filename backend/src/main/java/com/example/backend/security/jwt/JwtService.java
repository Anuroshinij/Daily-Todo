package com.example.backend.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mySuperSecretKeyForJwtAuthentication123456789";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes()
        );
    }

    public String generateToken(String userName) {
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(
                    new Date(
                        System.currentTimeMillis() + 1000 * 60 * 60
                    )
                )
                .signWith(getSigningKey())
                .compact();
    }

}

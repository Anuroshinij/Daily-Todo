package com.example.backend.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
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

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(
            String token, 
            Function<Claims, T> claimsResolver) {
                
        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token)
                        .before(new Date());
    }

    public boolean isTokenValid(String token, String userName) {
        String extractedUserName = extractUserName(token);

        return extractedUserName.equals(userName) && !isTokenExpired(token);
    }

}

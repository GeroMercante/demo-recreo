package com.recreo.utils;

import com.recreo.entities.Credential;
import com.recreo.exceptions.RecreoApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Component
public class JwtUtils {
    private SecretKey secretKey;
    private static final long EXPIRATION_TIME = AppConstant.TOKEN_EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String secretString;

    @PostConstruct
    public void init() {
        if (secretString != null) {
            byte[] keyBytes = Base64.getDecoder().decode(secretString);
            this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        }
    }

    public String generateToken(Credential credential) {
        return Jwts.builder()
                .subject(credential.getUser().getEmail())
                .claim("userId", credential.getUser().getId())
                .claim("isTemporary", credential.getIsTemporary())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsFunction) {
        return claimsFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Long getUserIdFromToken(String token) {
        return extractClaims(token, claims -> claims.get("userId", Long.class));
    }

    public Boolean getIsTemporary(String token) {
        return extractClaims(token, claims -> claims.get("isTemporary", Boolean.class));
    }

    public String extractUsername(String token) { return extractClaims(token, Claims::getSubject); }

    public boolean isTokenExpired(String token) { return extractClaims(token, Claims::getExpiration).before(new Date()); }

    public static String getAndValidateToken(String authHeader) throws RecreoApiException {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new RecreoApiException("No se puede establecer el token.");
        }
    }
}
package com.recreo.services.impl;

import com.recreo.entities.RevokedToken;
import com.recreo.repositories.TokenRepository;
import com.recreo.services.RevokedTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RevokedTokenServiceImpl implements RevokedTokenService {

    private final TokenRepository tokenRepository;

    public RevokedTokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void revokeToken(String token) {
        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setToken(token);
        revokedToken.setRevokedAt(LocalDateTime.now());
        tokenRepository.save(revokedToken);
    }

    @Override
    public boolean isTokenRevoked(String token) {
        return tokenRepository.existsByToken(token);
    }
}

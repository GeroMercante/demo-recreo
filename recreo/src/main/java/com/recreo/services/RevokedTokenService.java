package com.recreo.services;

public interface RevokedTokenService {
    void revokeToken(String token);
    boolean isTokenRevoked(String token);
}

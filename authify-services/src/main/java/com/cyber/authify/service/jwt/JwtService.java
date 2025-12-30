package com.cyber.authify.service.jwt;

public interface JwtService {

    String generateAccessToken(long userId, String email);

    String generateRefreshToken(long userId, String email);

    String getEmail(String token);

    boolean isTokenValid(String token);

    boolean isAccessToken(String token);

    boolean isRefreshToken(String token);
}

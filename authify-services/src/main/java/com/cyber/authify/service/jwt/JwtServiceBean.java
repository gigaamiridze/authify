package com.cyber.authify.service.jwt;

import com.cyber.authify.model.enums.JwtTokenType;
import com.cyber.authify.model.properties.JwtProperties;
import com.cyber.authify.utils.jwt.JwtClaim;
import com.cyber.authify.utils.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceBean implements JwtService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RSAPrivateKey privateKey;

    @Autowired
    private RSAPublicKey publicKey;

    @Override
    public String generateAccessToken(long userId, String email) {
        return buildToken(JwtTokenType.ACCESS, userId, email, jwtProperties.getAccessTokenExpiration());
    }

    @Override
    public String generateRefreshToken(long userId, String email) {
        return buildToken(JwtTokenType.REFRESH, userId, email, jwtProperties.getRefreshTokenExpiration());
    }

    @Override
    public String getEmail(String token) {
        Claims claims = getAllClaims(token);
        return JwtUtils.extractClaim(claims, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean isAccessToken(String token) {
        JwtTokenType tokenType = getTokenType(token);
        return tokenType != null && tokenType.equals(JwtTokenType.ACCESS);
    }

    @Override
    public boolean isRefreshToken(String token) {
        JwtTokenType tokenType = getTokenType(token);
        return tokenType != null && tokenType.equals(JwtTokenType.REFRESH);
    }

    private String buildToken(JwtTokenType jwtTokenType, long userId, String email, Duration duration) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(duration.toMillis());
        Map<String, Object> claims = JwtUtils.buildClaims(jwtTokenType.name(), userId);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(privateKey)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        Claims claims = getAllClaims(token);
        return JwtUtils.extractClaim(claims, Claims::getExpiration);
    }

    private JwtTokenType getTokenType(String token) {
        Claims claims = getAllClaims(token);
        String tokenType = JwtUtils.getClaim(claims, JwtClaim.TOKEN_TYPE, String.class);
        return JwtTokenType.valueOf(tokenType);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

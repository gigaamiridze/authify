package com.cyber.authify.utils.jwt;

import com.cyber.authify.utils.string.StringUtils;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtils {

    public static String getJwtFromRequest(String authorizationHeader) {
        if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public static Map<String, Object> buildClaims(String jwtTokenType, long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaim.TOKEN_TYPE, jwtTokenType);
        claims.put(JwtClaim.USER_ID, userId);
        return claims;
    }

    public static <T> T getClaim(Claims claims, String claimName, Class<T> type) {
        return extractClaim(claims, clm -> clm.get(claimName, type));
    }

    public static <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }
}

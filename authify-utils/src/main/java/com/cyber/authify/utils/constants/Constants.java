package com.cyber.authify.utils.constants;

public class Constants {

    public static final String SYSTEM_USERNAME = "System";

    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/auth/refresh-token",
            "/api/v1/auth/reset-password",
            "/api/v1/auth/send-reset-otp",
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
}

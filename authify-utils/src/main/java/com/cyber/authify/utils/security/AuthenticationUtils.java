package com.cyber.authify.utils.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        Authentication auth = getCurrentAuthentication();
        return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
    }

    public static String getAuthenticatedUserName() {
        Authentication auth = getCurrentAuthentication();
        return isAuthenticated() ? auth.getName() : null;
    }

    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}

package com.cyber.authify.service.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.cyber.authify.utils.constants.Constants.SYSTEM_USERNAME;

@Component
public class AuthenticationContextService {

    public Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public Optional<String> getUsername() {
        return getAuthentication()
                .filter(this::isRealUserAuthentication)
                .map(Authentication::getName);
    }

    public boolean isAuthenticated() {
        return getUsername().isPresent();
    }

    public String getCurrentUsernameOrSystem() {
        return getUsername().orElse(SYSTEM_USERNAME);
    }

    public void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private boolean isRealUserAuthentication(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }
}

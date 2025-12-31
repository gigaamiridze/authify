package com.cyber.authify.configuration;

import com.cyber.authify.service.auth.AuthenticationContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareBean implements AuditorAware<String> {

    @Autowired
    private AuthenticationContextService authenticationContextService;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(authenticationContextService.getCurrentUsernameOrSystem());
    }
}

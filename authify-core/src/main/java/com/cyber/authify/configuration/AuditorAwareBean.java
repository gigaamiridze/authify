package com.cyber.authify.configuration;

import com.cyber.authify.utils.security.AuthenticationUtils;
import com.cyber.authify.utils.string.StringUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.cyber.authify.utils.constants.Constants.SYSTEM_USERNAME;

@Component
public class AuditorAwareBean implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String name = AuthenticationUtils.getAuthenticatedUserName();
        String auditor = StringUtils.getOrDefault(name, SYSTEM_USERNAME);
        return Optional.of(auditor);
    }
}

package com.cyber.authify.authentication.provider;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.service.systemuser.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SystemUserService systemUserService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);

        if (!passwordEncoder.matches(password, systemUser.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(systemUser.getPermissionCodes());
        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

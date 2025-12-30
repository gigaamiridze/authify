package com.cyber.authify.authentication.filter;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.service.jwt.JwtService;
import com.cyber.authify.service.systemuser.SystemUserService;
import com.cyber.authify.utils.constants.Constants;
import com.cyber.authify.utils.jwt.JwtUtils;
import com.cyber.authify.utils.security.AuthenticationUtils;
import com.cyber.authify.utils.string.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        return Arrays.stream(Constants.PUBLIC_ENDPOINTS)
                .anyMatch(pattern -> PATH_MATCHER.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtUtils.getJwtFromRequest(request);
        if (StringUtils.isNotBlank(token) && jwtService.isAccessToken(token) && jwtService.isTokenValid(token)) {
            String email = jwtService.getEmail(token);
            SystemUser systemUser = systemUserService.getSystemUserWithGroupsAndPermissions(email);
            if (systemUser != null) {
                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(systemUser.getPermissionCodes());
                Authentication authentication = new UsernamePasswordAuthenticationToken(systemUser.getEmail(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            AuthenticationUtils.clearAuthentication();
        }
        filterChain.doFilter(request, response);
    }
}

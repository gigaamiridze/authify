package com.cyber.authify.authentication.handler;

import com.cyber.authify.model.dto.ErrorInfo;
import com.cyber.authify.model.enums.ErrorCode;
import com.cyber.authify.utils.provider.ObjectMapperProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapperProvider objectMapperProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);

        ErrorInfo errorInfo = ErrorInfo.builder()
                .status(status)
                .error("Unauthorized")
                .message(authException.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .errorCode(ErrorCode.UNAUTHORIZED)
                .build();

        String json = objectMapperProvider.toJson(errorInfo);
        response.getWriter().write(json);
    }
}

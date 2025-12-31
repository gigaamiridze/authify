package com.cyber.authify.authentication.handler;

import com.cyber.authify.model.dto.ErrorInfo;
import com.cyber.authify.model.enums.ErrorCode;
import com.cyber.authify.provider.ObjectMapperProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapperProvider objectMapperProvider;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        int status = HttpServletResponse.SC_FORBIDDEN;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);

        ErrorInfo errorInfo = ErrorInfo.builder()
                .status(status)
                .error("Forbidden")
                .message(accessDeniedException.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .errorCode(ErrorCode.ACCESS_DENIED)
                .build();

        String json = objectMapperProvider.toJson(errorInfo);
        response.getWriter().write(json);
    }
}

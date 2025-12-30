package com.cyber.authify.portal.handler;

import com.cyber.authify.model.dto.ErrorInfo;
import com.cyber.authify.model.enums.ErrorCode;
import com.cyber.authify.service.exception.AuthifyException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AuthifyException.class)
    public ResponseEntity<ErrorInfo> handleAuthifyException(AuthifyException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus status = getHttpStatus(errorCode);
        ErrorInfo errorInfo = buildErrorInfo(status, request, errorCode, ex.getMessage(), ex.getArguments());
        return ResponseEntity.status(status).body(errorInfo);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> validationErrors = getValidationErrors(ex);
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
        HttpStatus status = getHttpStatus(errorCode);
        return buildErrorInfo(status, request, errorCode, "Validation failed", validationErrors);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorInfo handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        HttpStatus status = getHttpStatus(errorCode);
        return buildErrorInfo(status, request, errorCode, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorInfo handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.ACCESS_DENIED;
        HttpStatus status = getHttpStatus(errorCode);
        return buildErrorInfo(status, request, errorCode, ex.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        HttpStatus status = getHttpStatus(errorCode);
        return buildErrorInfo(status, request, errorCode, ex.getMessage(), null);
    }

    private ErrorInfo buildErrorInfo(HttpStatus status, HttpServletRequest request, ErrorCode errorCode, String message, Map<String, String> arguments) {
        return ErrorInfo.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .errorCode(errorCode)
                .arguments(arguments)
                .build();
    }

    private HttpStatus getHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case VALIDATION_FAILED, EXPIRED_OTP, INVALID_OTP, INVALID_TOKEN_TYPE -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED, INVALID_CREDENTIALS, INVALID_OR_EXPIRED_TOKEN -> HttpStatus.UNAUTHORIZED;
            case ACCESS_DENIED -> HttpStatus.FORBIDDEN;
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case EMAIL_ALREADY_EXISTS, EMAIL_ALREADY_VERIFIED -> HttpStatus.CONFLICT;
            case EMAIL_SEND_FAILED, INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private Map<String, String> getValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return erros;
    }
}

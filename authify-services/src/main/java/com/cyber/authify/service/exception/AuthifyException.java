package com.cyber.authify.service.exception;

import com.cyber.authify.model.enums.ErrorCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class AuthifyException extends RuntimeException {

    private final ErrorCode errorCode;

    private final Map<String, String> arguments;

    public AuthifyException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.arguments = null;
    }

    public AuthifyException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.arguments = null;
    }

    public AuthifyException(ErrorCode errorCode, String message, Map<String, String> arguments) {
        super(message);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }
}

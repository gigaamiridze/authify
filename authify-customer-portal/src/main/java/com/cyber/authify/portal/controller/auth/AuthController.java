package com.cyber.authify.portal.controller.auth;

import com.cyber.authify.model.dto.auth.LoginRequest;
import com.cyber.authify.model.dto.auth.LoginResponse;
import com.cyber.authify.model.dto.auth.RefreshTokenRequest;
import com.cyber.authify.model.dto.auth.RefreshTokenResponse;
import com.cyber.authify.model.dto.auth.RegisterRequest;
import com.cyber.authify.model.dto.auth.RegisterResponse;
import com.cyber.authify.model.dto.auth.ResetPasswordRequest;
import com.cyber.authify.model.dto.email.VerifyEmailRequest;
import com.cyber.authify.model.dto.otp.SendOtpRequest;
import com.cyber.authify.model.entity.systemuser.SystemPermission;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/auth")
public interface AuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    RegisterResponse register(@Valid @RequestBody RegisterRequest request);

    @PostMapping("/login")
    LoginResponse login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response);

    @PostMapping("/refresh-token")
    RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request);

    @PostMapping("/reset-password")
    ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request);

    @PostMapping("/verify-email")
    @SecurityRequirement(name = "bearerAuth")
    @Secured(SystemPermission.SYSTEM_USER_VERIFY_EMAIL)
    ResponseEntity<Void> verifyEmail(@Valid @RequestBody VerifyEmailRequest request);

    @PostMapping("/send-reset-otp")
    ResponseEntity<Void> sendResetOtp(@Valid @RequestBody SendOtpRequest request);

    @PostMapping("/send-verify-otp")
    @SecurityRequirement(name = "bearerAuth")
    @Secured(SystemPermission.SYSTEM_USER_SEND_VERIFY_OTP)
    ResponseEntity<Void> sendVerifyOtp(@Valid @RequestBody SendOtpRequest request);
}

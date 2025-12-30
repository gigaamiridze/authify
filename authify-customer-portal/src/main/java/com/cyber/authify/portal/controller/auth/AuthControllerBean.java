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
import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.model.enums.OtpType;
import com.cyber.authify.service.auth.AuthService;
import com.cyber.authify.service.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth Controller")
public class AuthControllerBean implements AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthMapper authMapper;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        SystemUser systemUser = authService.register(request.getFirstName(),
                                                     request.getLastName(),
                                                     request.getEmail(),
                                                     request.getPassword());
        return authMapper.toRegisterResponse(systemUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        SystemUser systemUser = authService.login(request.getEmail(), request.getPassword());
        LoginResponse response = authMapper.toLoginResponse(systemUser);
        response.setAccessToken(jwtService.generateAccessToken(systemUser.getId(), systemUser.getEmail()));
        response.setRefreshToken(jwtService.generateRefreshToken(systemUser.getId(), systemUser.getEmail()));
        return response;
    }

    @Override
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return ResponseEntity.ok().build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String newAccessToken = authService.refreshToken(request.getRefreshToken());
        return new RefreshTokenResponse(newAccessToken);
    }

    @Override
    public ResponseEntity<Void> resetPassword(ResetPasswordRequest request) {
        authService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> verifyEmail(VerifyEmailRequest request) {
        authService.verifyEmail(request.getEmail(), request.getOtp());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> sendResetOtp(SendOtpRequest request) {
        authService.sendOtp(OtpType.RESET, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> sendVerifyOtp(SendOtpRequest request) {
        authService.sendOtp(OtpType.VERIFY, request.getEmail());
        return ResponseEntity.noContent().build();
    }
}

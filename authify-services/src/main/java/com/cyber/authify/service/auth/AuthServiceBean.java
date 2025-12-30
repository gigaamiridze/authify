package com.cyber.authify.service.auth;

import com.cyber.authify.model.dto.otp.OtpConfig;
import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.model.entity.systemuser.SystemUserGroup;
import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import com.cyber.authify.model.enums.ErrorCode;
import com.cyber.authify.model.enums.OtpType;
import com.cyber.authify.model.properties.OtpProperties;
import com.cyber.authify.service.email.EmailService;
import com.cyber.authify.service.event.publisher.UserRegisteredEventPublisher;
import com.cyber.authify.service.exception.AuthifyException;
import com.cyber.authify.service.jwt.JwtService;
import com.cyber.authify.service.systemuser.SystemUserGroupService;
import com.cyber.authify.service.systemuser.SystemUserService;
import com.cyber.authify.utils.otp.OtpUtils;
import com.cyber.authify.utils.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthServiceBean implements AuthService {

    @Autowired
    private UserRegisteredEventPublisher userRegisteredEventPublisher;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SystemUserGroupService userGroupService;

    @Autowired
    private SystemUserService systemUserService;

    @Lazy
    @Autowired
    private AuthServiceBean SELF;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OtpProperties otpProperties;

    @Override
    public SystemUser register(String firstName, String lastName, String email, String password) {
        SystemUserGroup userGroup = userGroupService.getUserGroupByCode(SystemUserGroupCode.SYSTEM_USER);
        SystemUser systemUser = systemUserService.createSystemUser(firstName, lastName, email, password, userGroup);
        userRegisteredEventPublisher.publishUserRegisteredEvent(systemUser);
        return systemUser;
    }

    @Override
    public SystemUser login(String email, String password) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        return systemUserService.getSystemUserByEmail(authentication.getName());
    }

    @Override
    public String refreshToken(String refreshToken) {
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new AuthifyException(ErrorCode.INVALID_TOKEN_TYPE, "Provided token is not a refresh token");
        }

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new AuthifyException(ErrorCode.INVALID_OR_EXPIRED_TOKEN, "Refresh token is invalid or expired");
        }

        String email = jwtService.getEmail(refreshToken);
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);
        return jwtService.generateAccessToken(systemUser.getId(), systemUser.getEmail());
    }

    @Override
    @Transactional
    public void resetPassword(String email, String otp, String newPassword) {
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);
        String resetOtp = systemUser.getResetOtp();
        if (StringUtils.isBlank(resetOtp) || !resetOtp.equals(otp)) {
            throw new AuthifyException(ErrorCode.INVALID_OTP, "Provided OTP is invalid");
        }

        if (OtpUtils.isExpiredOtp(systemUser.getResetOtpExpiresAt())) {
            throw new AuthifyException(ErrorCode.EXPIRED_OTP, "Provided OTP is expired");
        }

        systemUser.setPassword(passwordEncoder.encode(newPassword));
        systemUser.setResetOtp(null);
        systemUser.setResetOtpExpiresAt(null);
    }

    @Override
    @Transactional
    public void verifyEmail(String email, String otp) {
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);
        String verifyOtp = systemUser.getVerifyOtp();
        if (StringUtils.isBlank(verifyOtp) || !verifyOtp.equals(otp)) {
            throw new AuthifyException(ErrorCode.INVALID_OTP, "Provided OTP is invalid");
        }

        if (OtpUtils.isExpiredOtp(systemUser.getVerifyOtpExpiresAt())) {
            throw new AuthifyException(ErrorCode.EXPIRED_OTP, "Provided OTP is expired");
        }

        systemUser.setEmailVerified(true);
        systemUser.setVerifyOtp(null);
        systemUser.setVerifyOtpExpiresAt(null);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendOtp(OtpType otpType, String email) {
        switch (otpType) {
            case RESET -> SELF.sendResetOtp(email);
            case VERIFY -> SELF.sendVerifyOtp(email);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendResetOtp(String email) {
        OtpType otpType = OtpType.RESET;
        OtpConfig otpConfig = otpProperties.getReset();
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);

        String otp = OtpUtils.generateOtp(otpConfig.getLength());
        LocalDateTime expiresAt = OtpUtils.getOtpExpirationDate(otpConfig.getExpiration());
        systemUser.setResetOtp(otp);
        systemUser.setResetOtpExpiresAt(expiresAt);

        emailService.sendOtpEmail(otpType, systemUser.getEmail(), otp);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendVerifyOtp(String email) {
        OtpType otpType = OtpType.VERIFY;
        OtpConfig otpConfig = otpProperties.getVerify();
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);
        if (systemUser.isEmailVerified()) {
            throw new AuthifyException(
                    ErrorCode.EMAIL_ALREADY_VERIFIED,
                    String.format("Email %s has already been verified", email));
        }

        String otp = OtpUtils.generateOtp(otpConfig.getLength());
        LocalDateTime expiresAt = OtpUtils.getOtpExpirationDate(otpConfig.getExpiration());

        systemUser.setVerifyOtp(otp);
        systemUser.setVerifyOtpExpiresAt(expiresAt);

        emailService.sendOtpEmail(otpType, systemUser.getEmail(), otp);
    }
}

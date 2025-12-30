package com.cyber.authify.service.auth;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.model.enums.OtpType;

public interface AuthService {

    SystemUser register(String firstName, String lastName, String email, String password);

    SystemUser login(String email, String password);

    String refreshToken(String refreshToken);

    void resetPassword(String email, String otp, String newPassword);

    void verifyEmail(String email, String otp);

    void sendOtp(OtpType otpType, String email);
}

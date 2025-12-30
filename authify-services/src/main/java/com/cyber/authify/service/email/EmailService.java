package com.cyber.authify.service.email;

import com.cyber.authify.model.enums.OtpType;

public interface EmailService {

    void sendSimpleEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String htmlBody);

    void sendEmailWithAttachment(String to, String subject, String body, String filePath);

    void sendWelcomeEmail(String to, String name);

    void sendOtpEmail(OtpType otpType, String to, String otp);
}

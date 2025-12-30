package com.cyber.authify.utils.constants;

public class EmailTemplates {

    public static final String WELCOME_SUBJECT = "Welcome to Our Platform";
    public static final String RESET_OTP_SUBJECT = "Password Reset OTP";
    public static final String VERIFY_OTP_SUBJECT = "Email Verification OTP";

    public static final String WELCOME_TEMPLATE = """
            Hello %s,
            
            Welcome to Authify! Your account has been successfully created.
            We’re excited to have you on board.
            
            Best regards,
            Authify Team
            """;

    public static final String RESET_OTP_TEMPLATE = """
            Hello,
            
            We received a request to reset your password.
            
            Your one-time password (OTP) is: %s
            
            This OTP will expire in %s.
            
            If you did not request a password reset, please ignore this email.
            
            Thanks,
            Authify Team
            """;

    public static final String VERIFY_OTP_TEMPLATE = """
            Hello,
            
            We received a request to verify your email.

            Your email verification code (OTP) is: %s

            This OTP will expire in %s.

            If you did not request this verification, please ignore this email.

            Thanks,
            Authify Team
            """;
}

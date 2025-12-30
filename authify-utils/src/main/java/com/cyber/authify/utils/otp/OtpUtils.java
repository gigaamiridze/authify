package com.cyber.authify.utils.otp;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

public class OtpUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateOtp(byte length) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int number = SECURE_RANDOM.nextInt(10);
            otp.append(number);
        }
        return otp.toString();
    }

    public static LocalDateTime getOtpExpirationDate(Duration expiration) {
        return LocalDateTime.now().plus(expiration);
    }

    public static boolean isExpiredOtp(LocalDateTime expiresAt) {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}

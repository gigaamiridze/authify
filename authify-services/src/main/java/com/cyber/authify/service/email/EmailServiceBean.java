package com.cyber.authify.service.email;

import com.cyber.authify.model.enums.ErrorCode;
import com.cyber.authify.model.enums.OtpType;
import com.cyber.authify.model.properties.OtpProperties;
import com.cyber.authify.service.exception.AuthifyException;
import com.cyber.authify.utils.constants.EmailTemplates;
import com.cyber.authify.utils.formatter.DurationFormatter;
import com.cyber.authify.utils.string.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceBean implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private OtpProperties otpProperties;

    @Async
    @Override
    public void sendSimpleEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(mailProperties.getUsername());
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (MailException ex) {
            throw new AuthifyException(ErrorCode.EMAIL_SEND_FAILED, ex.getMessage());
        }
    }

    @Async
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            message.setFrom(mailProperties.getUsername());
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MailException | MessagingException ex) {
            throw new AuthifyException(ErrorCode.EMAIL_SEND_FAILED, ex.getMessage());
        }
    }

    @Async
    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, String filePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            message.setFrom(mailProperties.getUsername());
            helper.setSubject(subject);
            helper.setText(body);

            ClassPathResource resource = new ClassPathResource(filePath);
            String fileName = StringUtils.getOrDefault(resource.getFilename(), "file");
            helper.addAttachment(fileName, resource);
            mailSender.send(message);
        } catch (MailException | MessagingException ex) {
            throw new AuthifyException(ErrorCode.EMAIL_SEND_FAILED, ex.getMessage());
        }
    }

    @Override
    public void sendWelcomeEmail(String to, String name) {
        String body = String.format(EmailTemplates.WELCOME_TEMPLATE, name);
        sendSimpleEmail(to, EmailTemplates.WELCOME_SUBJECT, body);
    }

    @Override
    public void sendOtpEmail(OtpType otpType, String to, String otp) {
        switch (otpType) {
            case RESET -> sendResetOtpEmail(to, otp);
            case VERIFY -> sendVerifyOtpEmail(to, otp);
        }
    }

    private void sendResetOtpEmail(String to, String otp) {
        String expirationText = DurationFormatter.format(otpProperties.getReset().getExpiration());
        String body = String.format(EmailTemplates.RESET_OTP_TEMPLATE, otp, expirationText);
        sendSimpleEmail(to, EmailTemplates.RESET_OTP_SUBJECT, body);
    }

    private void sendVerifyOtpEmail(String to, String otp) {
        String expirationText = DurationFormatter.format(otpProperties.getVerify().getExpiration());
        String body = String.format(EmailTemplates.VERIFY_OTP_TEMPLATE, otp, expirationText);
        sendSimpleEmail(to, EmailTemplates.VERIFY_OTP_SUBJECT, body);
    }
}

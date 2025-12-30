package com.cyber.authify.portal.controller.email;

import com.cyber.authify.model.dto.email.EmailWithAttachmentRequest;
import com.cyber.authify.model.dto.email.HtmlEmailRequest;
import com.cyber.authify.model.dto.email.SimpleEmailRequest;
import com.cyber.authify.service.email.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Email Controller")
public class EmailControllerBean implements EmailController {

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<Void> sendSimpleEmail(SimpleEmailRequest request) {
        emailService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getBody());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> sendHtmlEmail(HtmlEmailRequest request) {
        emailService.sendHtmlEmail(request.getTo(), request.getSubject(), request.getHtmlBody());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> sendEmailWithAttachment(EmailWithAttachmentRequest request) {
        emailService.sendEmailWithAttachment(request.getTo(),
                                             request.getSubject(),
                                             request.getBody(),
                                             request.getFilePath());
        return ResponseEntity.noContent().build();
    }
}

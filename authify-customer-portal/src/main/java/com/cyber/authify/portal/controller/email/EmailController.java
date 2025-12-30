package com.cyber.authify.portal.controller.email;

import com.cyber.authify.model.dto.email.EmailWithAttachmentRequest;
import com.cyber.authify.model.dto.email.HtmlEmailRequest;
import com.cyber.authify.model.dto.email.SimpleEmailRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/email")
public interface EmailController {

    @PostMapping("/send-simple")
    ResponseEntity<Void> sendSimpleEmail(@Valid @RequestBody SimpleEmailRequest request);

    @PostMapping("/send-html")
    ResponseEntity<Void> sendHtmlEmail(@Valid @RequestBody HtmlEmailRequest request);

    @PostMapping("/send-attachment")
    ResponseEntity<Void> sendEmailWithAttachment(@Valid @RequestBody EmailWithAttachmentRequest request);
}

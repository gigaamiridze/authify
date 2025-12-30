package com.cyber.authify.service.event.listener;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.service.email.EmailService;
import com.cyber.authify.service.event.model.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventListener {

    @Autowired
    private EmailService emailService;

    @Async
    @EventListener
    public void onUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        SystemUser systemUser = userRegisteredEvent.getSystemUser();
        emailService.sendWelcomeEmail(systemUser.getEmail(), systemUser.getFullName());
    }
}

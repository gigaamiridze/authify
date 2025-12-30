package com.cyber.authify.service.event.publisher;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.service.event.model.UserRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUserRegisteredEvent(SystemUser systemUser) {
        UserRegisteredEvent event = new UserRegisteredEvent(systemUser);
        applicationEventPublisher.publishEvent(event);
    }
}

package com.cyber.authify.service.event.model;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private final SystemUser systemUser;

    public UserRegisteredEvent(SystemUser systemUser) {
        super(systemUser);
        this.systemUser = systemUser;
    }
}

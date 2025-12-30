package com.cyber.authify.model.properties;

import com.cyber.authify.model.dto.Contact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "authify.core.openapi")
public class OpenApiProperties {

    private String title;

    private String description;

    private Contact contact;
}

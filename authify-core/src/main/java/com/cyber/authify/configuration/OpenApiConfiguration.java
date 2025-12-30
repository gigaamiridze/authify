package com.cyber.authify.configuration;

import com.cyber.authify.model.properties.AuthifyProperties;
import com.cyber.authify.model.properties.OpenApiProperties;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
@Configuration
public class OpenApiConfiguration {

    @Autowired
    private AuthifyProperties authifyProperties;

    @Autowired
    private OpenApiProperties openApiProperties;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version(authifyProperties.getVersion())
                .title(openApiProperties.getTitle())
                .description(openApiProperties.getDescription())
                .contact(getContactDetails());
        return new OpenAPI().info(info);
    }

    private Contact getContactDetails() {
        com.cyber.authify.model.dto.Contact contact = openApiProperties.getContact();
        return new Contact()
                .name(contact.getName())
                .email(contact.getEmail())
                .url(contact.getUrl());
    }
}

package com.cyber.authify.model.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "authify")
public class AuthifyProperties {

    private String version;

    private String shutdownToken;
}

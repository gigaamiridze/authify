package com.cyber.authify.model.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "authify.core.jwt")
public class JwtProperties {

    @NotBlank
    private String issuer;

    @NotBlank
    private String keyAlias;

    @NotBlank
    private String keyStorePath;

    @NotBlank
    private String keyStoreType;

    @NotBlank
    private String keyStorePassword;

    @NotBlank
    private String privateKeyPassphrase;

    @NotNull
    private Duration accessTokenExpiration;

    @NotNull
    private Duration refreshTokenExpiration;
}

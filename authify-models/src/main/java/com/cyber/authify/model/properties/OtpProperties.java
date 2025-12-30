package com.cyber.authify.model.properties;

import com.cyber.authify.model.dto.otp.OtpConfig;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "authify.core.otp")
public class OtpProperties {

    @Valid
    private OtpConfig reset;

    @Valid
    private OtpConfig verify;
}

package com.cyber.authify.portal.controller.configuration;

import com.cyber.authify.model.dto.otp.OtpConfigDTO;
import com.cyber.authify.model.properties.OtpProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Configuration Controller")
public class ConfigurationControllerBean implements ConfigurationController {

    @Autowired
    private OtpProperties otpProperties;

    @Override
    public OtpConfigDTO getOtpConfig() {
        return OtpConfigDTO.builder()
                .resetOtpLength(otpProperties.getReset().getLength())
                .verifyOtpLength(otpProperties.getVerify().getLength())
                .build();
    }
}

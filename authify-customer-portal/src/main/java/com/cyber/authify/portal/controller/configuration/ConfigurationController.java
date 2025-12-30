package com.cyber.authify.portal.controller.configuration;

import com.cyber.authify.model.dto.otp.OtpConfigDTO;
import com.cyber.authify.model.entity.systemuser.SystemPermission;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/configuration")
public interface ConfigurationController {

    @GetMapping("/otp")
    @Secured(SystemPermission.SYSTEM_USER_READ_OTP_CONFIGURATION)
    OtpConfigDTO getOtpConfig();
}

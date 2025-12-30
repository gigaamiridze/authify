package com.cyber.authify.portal.controller.shutdown;

import com.cyber.authify.model.dto.ShutdownRequest;
import com.cyber.authify.model.entity.systemuser.SystemPermission;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/shutdown")
public interface ShutdownController {

    @PostMapping
    @Secured(SystemPermission.ADMIN_SHUTDOWN_SYSTEM)
    ResponseEntity<String> shutdown(@Valid @RequestBody ShutdownRequest request);
}

package com.cyber.authify.portal.controller.shutdown;

import com.cyber.authify.model.dto.ShutdownRequest;
import com.cyber.authify.model.properties.AuthifyProperties;
import com.cyber.authify.service.shutdown.ShutdownService;
import com.cyber.authify.utils.string.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Shutdown Controller")
public class ShutdownControllerBean implements ShutdownController {

    @Autowired
    private ShutdownService shutdownService;

    @Autowired
    private AuthifyProperties authifyProperties;

    @Override
    public ResponseEntity<String> shutdown(ShutdownRequest request) {
        if (isValidShutdownToken(request.getShutdownToken())) {
            shutdownService.shutdown();
            return ResponseEntity.ok("Application shutdown initiated - background cleanup in progress...");
        } else {
            return ResponseEntity.badRequest().body("Incorrect shutdown token");
        }
    }

    private boolean isValidShutdownToken(String token) {
        String shutdownToken = authifyProperties.getShutdownToken();
        return StringUtils.isNotBlank(shutdownToken) && shutdownToken.equals(token);
    }
}

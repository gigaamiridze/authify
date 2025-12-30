package com.cyber.authify.portal.controller.profile;

import com.cyber.authify.model.dto.ProfileResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/profile")
public interface ProfileController {

    @GetMapping("/{email}")
    @PreAuthorize("#email == authentication.name")
    ProfileResponse getProfileByEmail(@PathVariable String email);

    @GetMapping
    ProfileResponse getCurrentProfile(@CurrentSecurityContext(expression = "authentication?.name") String email);
}

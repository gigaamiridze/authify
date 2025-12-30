package com.cyber.authify.portal.controller.profile;

import com.cyber.authify.model.dto.ProfileResponse;
import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.service.systemuser.SystemUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Profile Controller")
public class ProfileControllerBean implements ProfileController {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public ProfileResponse getProfileByEmail(String email) {
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);
        return profileMapper.toProfileResponse(systemUser);
    }

    @Override
    public ProfileResponse getCurrentProfile(String email) {
        SystemUser systemUser = systemUserService.getSystemUserByEmail(email);
        return profileMapper.toProfileResponse(systemUser);
    }
}

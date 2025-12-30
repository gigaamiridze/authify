package com.cyber.authify.portal.controller.profile;

import com.cyber.authify.model.dto.ProfileResponse;
import com.cyber.authify.model.entity.systemuser.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    @Mapping(target = "permissions", expression = "java(systemUser.getPermissionCodes())")
    ProfileResponse toProfileResponse(SystemUser systemUser);
}

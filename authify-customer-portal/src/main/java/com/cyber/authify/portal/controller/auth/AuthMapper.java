package com.cyber.authify.portal.controller.auth;

import com.cyber.authify.model.dto.auth.LoginResponse;
import com.cyber.authify.model.dto.auth.RegisterResponse;
import com.cyber.authify.model.entity.systemuser.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthMapper {

    RegisterResponse toRegisterResponse(SystemUser systemUser);

    LoginResponse toLoginResponse(SystemUser systemUser);
}

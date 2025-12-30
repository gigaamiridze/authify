package com.cyber.authify.service.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import com.cyber.authify.model.entity.systemuser.SystemUserPermission;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SystemUserPermissionService {

    SystemUserPermission getUserPermissionById(long id);

    SystemUserPermission getUserPermissionByCode(String code);

    List<SystemUserPermission> getAllUserPermissions();

    Map<String, SystemUserPermission> getUserPermissionsByCode();

    Map<SystemUserGroupCode, List<SystemUserPermission>> getGroupedUserPermissionsByGroupCode();

    SystemUserPermission createUserPermission(String code, SystemUserGroupCode userGroupCode);

    List<SystemUserPermission> saveUserPermissions(Collection<SystemUserPermission> userPermissions);
}

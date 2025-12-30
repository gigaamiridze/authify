package com.cyber.authify.service.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUserGroup;
import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import com.cyber.authify.model.entity.systemuser.SystemUserPermission;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SystemUserGroupService {

    SystemUserGroup getUserGroupById(long id);

    SystemUserGroup getUserGroupByCode(SystemUserGroupCode code);

    List<SystemUserGroup> getAllUserGroups();

    List<SystemUserGroup> getAllUserGroupsWithPermissions();

    Map<SystemUserGroupCode, SystemUserGroup> getUserGroupsByCode();

    SystemUserGroup createUserGroup(SystemUserGroupCode code, Set<SystemUserPermission> userPermissions);

    List<SystemUserGroup> saveUserGroups(Collection<SystemUserGroup> userGroups);
}

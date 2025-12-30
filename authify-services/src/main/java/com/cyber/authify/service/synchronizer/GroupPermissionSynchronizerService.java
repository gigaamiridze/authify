package com.cyber.authify.service.synchronizer;

import com.cyber.authify.model.entity.systemuser.Permission;
import com.cyber.authify.model.entity.systemuser.SystemPermission;
import com.cyber.authify.model.entity.systemuser.SystemUserGroup;
import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import com.cyber.authify.model.entity.systemuser.SystemUserPermission;
import com.cyber.authify.service.systemuser.SystemUserGroupService;
import com.cyber.authify.service.systemuser.SystemUserPermissionService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class GroupPermissionSynchronizerService {

    @Autowired
    private SystemUserGroupService userGroupService;

    @Autowired
    private SystemUserPermissionService userPermissionService;

    @PostConstruct
    public void sync() {
        log.info("Starting user groups and permissions synchronization...");
        syncUserGroups();
        syncUserPermissions();
        attachPermissionsToGroup();
        log.info("User groups and permissions synchronization completed");
    }

    private void syncUserGroups() {
        Map<SystemUserGroupCode, SystemUserGroup> userGroupsByCode = userGroupService.getUserGroupsByCode();
        Set<SystemUserGroup> userGroupsToSave = new HashSet<>();

        for (SystemUserGroupCode groupCode : SystemUserGroupCode.values()) {
            if (!userGroupsByCode.containsKey(groupCode)) {
                log.info("Creating user group: {}", groupCode);
                SystemUserGroup userGroup = SystemUserGroup.builder()
                        .code(groupCode)
                        .systemUserPermissions(new HashSet<>())
                        .build();
                userGroupsToSave.add(userGroup);
            }
        }

        saveUserGroups(userGroupsToSave);
    }

    private void syncUserPermissions() {
        Set<SystemUserPermission> scannedPermissions = scanPermissions();
        Map<String, SystemUserPermission> userPermissionsByCode = userPermissionService.getUserPermissionsByCode();
        Set<SystemUserPermission> userPermissionsToSave = new HashSet<>();

        for (SystemUserPermission scanned : scannedPermissions) {
            String permissionCode = scanned.getCode();
            SystemUserGroupCode groupCode = scanned.getGroupCode();
            SystemUserPermission existing = userPermissionsByCode.get(permissionCode);

            if (existing == null) {
                log.info("Creating user permission: {} for user group: {}", permissionCode, groupCode);
                userPermissionsToSave.add(scanned);
            } else if (isGroupCodeChanged(existing, scanned)) {
                log.info("Updating user permission: {} - User group changed from {} to {}",
                        existing.getCode(), existing.getGroupCode(), groupCode);
                existing.setGroupCode(groupCode);
                userPermissionsToSave.add(existing);
            }
        }

        saveUserPermissions(userPermissionsToSave);
    }

    private void attachPermissionsToGroup() {
        List<SystemUserGroup> userGroups = userGroupService.getAllUserGroupsWithPermissions();
        Map<SystemUserGroupCode, List<SystemUserPermission>> userPermissionsByGroup = userPermissionService.getGroupedUserPermissionsByGroupCode();

        for (SystemUserGroup userGroup : userGroups) {
            SystemUserGroupCode groupCode = userGroup.getCode();
            Set<SystemUserPermission> currentPermissions = userGroup.getSystemUserPermissions();
            List<SystemUserPermission> expectedPermissions = userPermissionsByGroup.getOrDefault(groupCode, Collections.emptyList());

            for (SystemUserPermission permission : expectedPermissions) {
                if (!currentPermissions.contains(permission)) {
                    userGroup.addPermission(permission);
                }
            }
        }

        userGroupService.saveUserGroups(userGroups);
    }

    private void saveUserGroups(Collection<SystemUserGroup> userGroups) {
        if (!userGroups.isEmpty()) {
            userGroupService.saveUserGroups(userGroups);
            log.info("Saved {} user group(s)", userGroups.size());
        }
    }

    private void saveUserPermissions(Collection<SystemUserPermission> userPermissions) {
        if (!userPermissions.isEmpty()) {
            userPermissionService.saveUserPermissions(userPermissions);
            log.info("Saved {} user permission(s)", userPermissions.size());
        }
    }

    private boolean isGroupCodeChanged(SystemUserPermission existing, SystemUserPermission scanned) {
        return !existing.getGroupCode().equals(scanned.getGroupCode());
    }

    private Set<SystemUserPermission> scanPermissions() {
        Set<SystemUserPermission> permissions = new HashSet<>();
        Field[] fields = SystemPermission.class.getDeclaredFields();

        for (Field field : fields) {
            if (isValidPermissionField(field)) {
                SystemUserPermission permission = createPermissionFromField(field);
                permissions.add(permission);
            }
        }

        return permissions;
    }

    private SystemUserPermission createPermissionFromField(Field field) {
        Permission annotation = field.getAnnotation(Permission.class);
        SystemUserGroupCode groupCode = annotation.groupCode();
        String permissionCode = getFieldValue(field);

        return SystemUserPermission.builder()
                .code(permissionCode)
                .groupCode(groupCode)
                .build();
    }

    private boolean isValidPermissionField(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers) &&
                Modifier.isStatic(modifiers) &&
                Modifier.isFinal(modifiers) &&
                field.getType() == String.class &&
                field.isAnnotationPresent(Permission.class);
    }

    private String getFieldValue(Field field) {
        try {
            return (String) field.get(null);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Failed to access field value: " + field.getName());
        }
    }
}

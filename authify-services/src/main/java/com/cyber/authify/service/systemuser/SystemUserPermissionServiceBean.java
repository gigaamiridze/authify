package com.cyber.authify.service.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import com.cyber.authify.model.entity.systemuser.SystemUserPermission;
import com.cyber.authify.repository.systemuser.SystemUserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SystemUserPermissionServiceBean implements SystemUserPermissionService {

    @Autowired
    private SystemUserPermissionRepository userPermissionRepository;

    @Override
    public SystemUserPermission getUserPermissionById(long id) {
        return userPermissionRepository.findById(id).orElse(null);
    }

    @Override
    public SystemUserPermission getUserPermissionByCode(String code) {
        return userPermissionRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<SystemUserPermission> getAllUserPermissions() {
        return userPermissionRepository.findAll();
    }

    @Override
    public Map<String, SystemUserPermission> getUserPermissionsByCode() {
        List<SystemUserPermission> userPermissions = getAllUserPermissions();
        return userPermissions.stream()
                .collect(Collectors.toMap(
                        SystemUserPermission::getCode,
                        Function.identity()
                ));
    }

    @Override
    public Map<SystemUserGroupCode, List<SystemUserPermission>> getGroupedUserPermissionsByGroupCode() {
        List<SystemUserPermission> userPermissions = getAllUserPermissions();
        return userPermissions.stream()
                .collect(Collectors.groupingBy(SystemUserPermission::getGroupCode));
    }

    @Override
    public SystemUserPermission createUserPermission(String code, SystemUserGroupCode userGroupCode) {
        SystemUserPermission userPermission = SystemUserPermission.builder()
                .code(code)
                .groupCode(userGroupCode)
                .build();
        return userPermissionRepository.save(userPermission);
    }

    @Override
    public List<SystemUserPermission> saveUserPermissions(Collection<SystemUserPermission> userPermissions) {
        return userPermissionRepository.saveAll(userPermissions);
    }
}

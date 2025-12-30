package com.cyber.authify.service.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUserGroup;
import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import com.cyber.authify.model.entity.systemuser.SystemUserPermission;
import com.cyber.authify.repository.systemuser.SystemUserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SystemUserGroupServiceBean implements SystemUserGroupService {

    @Autowired
    private SystemUserGroupRepository userGroupRepository;

    @Override
    public SystemUserGroup getUserGroupById(long id) {
        return userGroupRepository.findById(id).orElse(null);
    }

    @Override
    public SystemUserGroup getUserGroupByCode(SystemUserGroupCode code) {
        return userGroupRepository.findByCode(code).orElse(null);
    }

    @Override
    public List<SystemUserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }

    @Override
    public List<SystemUserGroup> getAllUserGroupsWithPermissions() {
        return userGroupRepository.findAllWithPermissions();
    }

    @Override
    public Map<SystemUserGroupCode, SystemUserGroup> getUserGroupsByCode() {
        List<SystemUserGroup> userGroups = getAllUserGroups();
        return userGroups.stream()
                .collect(Collectors.toMap(
                        SystemUserGroup::getCode,
                        Function.identity()
                ));
    }

    @Override
    public SystemUserGroup createUserGroup(SystemUserGroupCode code, Set<SystemUserPermission> userPermissions) {
        SystemUserGroup userGroup = SystemUserGroup.builder()
                .code(code)
                .systemUserPermissions(userPermissions)
                .build();
        return userGroupRepository.save(userGroup);
    }

    @Override
    public List<SystemUserGroup> saveUserGroups(Collection<SystemUserGroup> userGroups) {
        return userGroupRepository.saveAll(userGroups);
    }
}

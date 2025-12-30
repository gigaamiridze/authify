package com.cyber.authify.service.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.model.entity.systemuser.SystemUserGroup;

import java.util.List;

public interface SystemUserService {

    SystemUser getSystemUserById(long id);

    SystemUser getSystemUserByEmail(String email);

    SystemUser getSystemUserWithGroupsAndPermissions(String email);

    List<SystemUser> getSystemUsers();

    SystemUser createSystemUser(String firstName,
                                String lastName,
                                String email,
                                String password,
                                SystemUserGroup... userGroups);

    void deleteSystemUserById(long id);

    void deleteSystemUserByEmail(String email);
}

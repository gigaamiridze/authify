package com.cyber.authify.service.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import com.cyber.authify.model.entity.systemuser.SystemUserGroup;
import com.cyber.authify.model.enums.ErrorCode;
import com.cyber.authify.repository.systemuser.SystemUserRepository;
import com.cyber.authify.service.exception.AuthifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SystemUserServiceBean implements SystemUserService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SystemUser getSystemUserById(long id) {
        return systemUserRepository.findById(id)
                .orElseThrow(() -> new AuthifyException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("User with ID %d not found", id))
                );
    }

    @Override
    public SystemUser getSystemUserByEmail(String email) {
        return systemUserRepository.findByEmail(email)
                .orElseThrow(() -> new AuthifyException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("User with email %s not found", email))
                );
    }

    @Override
    public SystemUser getSystemUserWithGroupsAndPermissions(String email) {
        return systemUserRepository.findSystemUserWithGroupsAndPermissions(email)
                .orElseThrow(() -> new AuthifyException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("User with email %s not found", email))
                );
    }

    @Override
    public List<SystemUser> getSystemUsers() {
        return systemUserRepository.findAll();
    }

    @Override
    public SystemUser createSystemUser(String firstName,
                                       String lastName,
                                       String email,
                                       String password,
                                       SystemUserGroup... userGroups) {
        if (systemUserRepository.existsByEmail(email)) {
            throw new AuthifyException(
                    ErrorCode.EMAIL_ALREADY_EXISTS,
                    String.format("Email %s already exists", email));
        }

        SystemUser systemUser = SystemUser.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .systemUserGroups(Set.of(userGroups))
                .build();
        return systemUserRepository.save(systemUser);
    }

    @Override
    public void deleteSystemUserById(long id) {
        systemUserRepository.deleteById(id);
    }

    @Override
    public void deleteSystemUserByEmail(String email) {
        systemUserRepository.deleteByEmail(email);
    }
}

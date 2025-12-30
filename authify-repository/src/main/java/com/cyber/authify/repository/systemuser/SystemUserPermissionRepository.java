package com.cyber.authify.repository.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserPermissionRepository extends JpaRepository<SystemUserPermission, Long> {

    Optional<SystemUserPermission> findByCode(String code);

    boolean existsByCode(String code);
}

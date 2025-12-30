package com.cyber.authify.repository.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUserGroup;
import com.cyber.authify.model.entity.systemuser.SystemUserGroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemUserGroupRepository extends JpaRepository<SystemUserGroup, Long> {

    Optional<SystemUserGroup> findByCode(SystemUserGroupCode code);

    @Query("SELECT DISTINCT g FROM SystemUserGroup g LEFT JOIN FETCH g.systemUserPermissions")
    List<SystemUserGroup> findAllWithPermissions();
}

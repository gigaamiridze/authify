package com.cyber.authify.repository.systemuser;

import com.cyber.authify.model.entity.systemuser.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByEmail(String email);

    @Query("""
        SELECT u FROM SystemUser u
        LEFT JOIN FETCH u.systemUserGroups g
        LEFT JOIN FETCH g.systemUserPermissions
        WHERE u.email = :email
    """)
    Optional<SystemUser> findSystemUserWithGroupsAndPermissions(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}

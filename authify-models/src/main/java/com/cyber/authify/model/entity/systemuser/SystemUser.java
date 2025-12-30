package com.cyber.authify.model.entity.systemuser;

import com.cyber.authify.model.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "system_users",
       indexes = {@Index(name = "email_index", columnList = "email", unique = true)
})
public class SystemUser extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean emailVerified;

    @Column(length = 8)
    private String verifyOtp;

    private LocalDateTime verifyOtpExpiresAt;

    @Column(length = 8)
    private String resetOtp;

    private LocalDateTime resetOtpExpiresAt;

    @ManyToMany
    @JoinTable(
            name = "system_user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<SystemUserGroup> systemUserGroups = new HashSet<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void addGroup(SystemUserGroup group) {
        if (group != null) {
            systemUserGroups.add(group);
        }
    }

    public void removeGroup(SystemUserGroup group) {
        if (group != null) {
            systemUserGroups.remove(group);
        }
    }

    public Set<SystemUserPermission> getPermissions() {
        return systemUserGroups.stream()
                .flatMap(group -> group.getSystemUserPermissions().stream())
                .collect(Collectors.toSet());
    }

    public Set<String> getPermissionCodes() {
        return getPermissions().stream()
                .map(SystemUserPermission::getCode)
                .collect(Collectors.toSet());
    }
}

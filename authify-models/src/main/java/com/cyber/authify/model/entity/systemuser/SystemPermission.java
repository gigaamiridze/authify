package com.cyber.authify.model.entity.systemuser;

/**
 * Central registry of system permission codes used for authorization.
 *
 * Permissions are defined as String constants and may be annotated with
 * {@link Permission} to associate them with a user group.
 *
 * Naming convention: UPPER_SNAKE_CASE. Format is {USER_GROUP}_{USER_PERMISSION}.
 * The constant name and value must be identical.
 *
 * This interface is not intended to be implemented.
 */
public interface SystemPermission {

    @Permission(groupCode = SystemUserGroupCode.SYSTEM_USER)
    String SYSTEM_USER_VERIFY_EMAIL = "SYSTEM_USER_VERIFY_EMAIL";

    @Permission(groupCode = SystemUserGroupCode.SYSTEM_USER)
    String SYSTEM_USER_SEND_VERIFY_OTP = "SYSTEM_USER_SEND_VERIFY_OTP";

    @Permission(groupCode = SystemUserGroupCode.SYSTEM_USER)
    String SYSTEM_USER_READ_OTP_CONFIGURATION = "SYSTEM_USER_READ_OTP_CONFIGURATION";

    @Permission(groupCode = SystemUserGroupCode.ADMIN)
    String ADMIN_SHUTDOWN_SYSTEM = "ADMIN_SHUTDOWN_SYSTEM";
}

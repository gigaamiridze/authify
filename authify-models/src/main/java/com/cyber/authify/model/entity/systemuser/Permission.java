package com.cyber.authify.model.entity.systemuser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Permission {

    SystemUserGroupCode groupCode() default SystemUserGroupCode.GUEST;
}

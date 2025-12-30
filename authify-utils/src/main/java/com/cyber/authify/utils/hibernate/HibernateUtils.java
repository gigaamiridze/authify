package com.cyber.authify.utils.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class HibernateUtils {

    public static <T> void initialize(T entity) {
        if (entity != null && !Hibernate.isInitialized(entity)) {
            Hibernate.initialize(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T unproxy(T entity) {
        if (entity instanceof HibernateProxy proxy) {
            return (T) proxy.getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    public static <T> T initializeAndUnProxy(T entity) {
        if (entity == null) {
            return null;
        }

        initialize(entity);
        return unproxy(entity);
    }
}

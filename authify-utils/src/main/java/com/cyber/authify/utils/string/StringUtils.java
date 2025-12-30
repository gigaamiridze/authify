package com.cyber.authify.utils.string;

public class StringUtils {

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static String getOrDefault(String value, String defaultValue) {
        return isNotBlank(value) ? value : defaultValue;
    }
}

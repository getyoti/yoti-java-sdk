package com.yoti.api.client.spi.remote.util;

import static java.lang.String.format;

public class Validation {

    public static <T> T notNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " must not be null.");
        }
        return value;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static void notNullOrEmpty(String value, String name) {
        if (isNullOrEmpty(value)) {
            throw new IllegalArgumentException(name + " must not be empty or null");
        }
    }

    public static void notGreaterThan(long value, long limit, String name) {
        if (value > limit) {
            throw new IllegalArgumentException(format("'%s' value '%s' is greater than '%s'", name, value, limit));
        }
    }

}

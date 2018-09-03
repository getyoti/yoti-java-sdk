package com.yoti.api.client.spi.remote.util;

import static java.lang.String.format;

public class Validation {

    public static <T> T notNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(format("'%s' must not be null.", name));
        }
        return value;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static void notNullOrEmpty(String value, String name) {
        if (isNullOrEmpty(value)) {
            throw new IllegalArgumentException(format("'%s' must not be empty or null", name));
        }
    }

    public static void notEqualTo(Object value, Object notAllowed, String name) {
        if (notEqualTo(value, notAllowed)) {
            throw new IllegalArgumentException(format("'%s' value may not be equalTo '%s'", name, notAllowed));
        }
    }

    private static boolean notEqualTo(Object value, Object notAllowed) {
        if (notAllowed == null) {
            return value == null;
        }
        return notAllowed.equals(value);
    }

    public static void notGreaterThan(long value, long limit, String name) {
        if (value > limit) {
            throw new IllegalArgumentException(format("'%s' value '%s' is greater than '%s'", name, value, limit));
        }
    }

    public static void notLessThan(long value, long limit, String name) {
        if (value < limit) {
            throw new IllegalArgumentException(format("'%s' value '%s' is less than '%s'", name, value, limit));
        }
    }

    public static void withinRange(long value, long minLimit, long maxLimit, String name) {
        notLessThan(value, minLimit, name);
        notGreaterThan(value, maxLimit, name);
    }

}

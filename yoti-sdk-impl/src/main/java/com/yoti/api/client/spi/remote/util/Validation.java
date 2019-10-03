package com.yoti.api.client.spi.remote.util;

import static java.lang.String.format;

import java.util.List;

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

    public static <T extends Comparable> void notGreaterThan(T value, T limit, String name) {
        if (value.compareTo(limit) > 0) {
            throw new IllegalArgumentException(format("'%s' value '%s' is greater than '%s'", name, value, limit));
        }
    }

    public static <T extends Comparable> void notLessThan(T value, T limit, String name) {
        if (value.compareTo(limit) < 0) {
            throw new IllegalArgumentException(format("'%s' value '%s' is less than '%s'", name, value, limit));
        }
    }

    public static <T extends Comparable> void withinRange(T value, T minLimit, T maxLimit, String name) {
        notLessThan(value, minLimit, name);
        notGreaterThan(value, maxLimit, name);
    }

    public static void matchesPattern(String value, String regex, String name) {
        if (value == null || ! value.matches(regex)) {
            throw new IllegalArgumentException(format("'%s' value '%s' does not match format '%s'", name, value, regex));
        }
    }

    public static <T> void withinList(T value, List<T> allowedValues) {
        if (!allowedValues.contains(value)) {
            throw new IllegalArgumentException(format("value '%s' is not in the list '%s'", value, allowedValues));
        }
    }

}

package com.yoti.api.client;

/**
 * Represents a generic, typed key/value pair, a basic building block of
 * {@link Profile} instances.
 *
 */
public final class Attribute {
    private final String name;
    private final Object value;

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> clazz) {
        return value != null ? (clazz.isAssignableFrom(value.getClass()) ? (T) value : null) : null;
    }

    public <T> T getValueOrDefault(Class<T> clazz, T defaultValue) {
        T result = getValue(clazz);
        return (result == null) ? defaultValue : result;
    }
}

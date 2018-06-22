package com.yoti.api.client;

import java.util.Set;

/**
 * Represents a generic, typed key/value pair, a basic building block of
 * {@link Profile} instances.
 *
 */
public final class Attribute<T> {

    private final String name;
    private final T value;
    private final Set<String> sources;
    private final Set<String> verifiers;

    public Attribute(String name, T value) {
        this(name, value, null);
    }
    
    public Attribute(String name, T value, Set<String> sources) {
        this(name, value, sources, null);
    }

    public Attribute(String name, T value, Set<String> sources, Set<String> verifiers) {
        this.name = name;
        this.value = value;
        this.sources = sources;
        this.verifiers = verifiers;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public Set<String> getSources() {
        return sources;
    }
    
    public Set<String> getVerifiers() {
        return verifiers;
    }

}

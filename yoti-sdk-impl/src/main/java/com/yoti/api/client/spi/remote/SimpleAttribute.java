package com.yoti.api.client.spi.remote;

import java.util.Set;

import com.yoti.api.client.Attribute;

public final class SimpleAttribute<T> implements Attribute<T> {

    private final String name;
    private final T value;
    private final Set<String> sources;
    private final Set<String> verifiers;

    public SimpleAttribute(String name, T value) {
        this(name, value, null);
    }
    
    public SimpleAttribute(String name, T value, Set<String> sources) {
        this(name, value, sources, null);
    }

    public SimpleAttribute(String name, T value, Set<String> sources, Set<String> verifiers) {
        this.name = name;
        this.value = value;
        this.sources = sources;
        this.verifiers = verifiers;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Set<String> getSources() {
        return sources;
    }
    
    @Override
    public Set<String> getVerifiers() {
        return verifiers;
    }

}

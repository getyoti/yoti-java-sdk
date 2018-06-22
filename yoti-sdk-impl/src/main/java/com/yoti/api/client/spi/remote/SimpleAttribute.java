package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.Collections;
import java.util.List;

import com.yoti.api.client.Anchor;
import com.yoti.api.client.Attribute;

public final class SimpleAttribute<T> implements Attribute<T> {

    private final String name;
    private final T value;
    private final List<Anchor> sources;
    private final List<Anchor> verifiers;
    private final List<Anchor> allAnchors;

    public SimpleAttribute(String name, T value) {
        this(name, value, Collections.<Anchor>emptyList(), Collections.<Anchor>emptyList(), Collections.<Anchor>emptyList());
    }

    public SimpleAttribute(String name, T value, List<Anchor> sources, List<Anchor> verifiers, List<Anchor> allAnchors) {
        this.name = name;
        this.value = value;
        this.sources = notNull(sources, "sources");
        this.verifiers = notNull(verifiers, "verifiers");
        this.allAnchors = notNull(allAnchors, "allAnchors");
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
    public List<Anchor> getSources() {
        return sources;
    }

    @Override
    public List<Anchor> getVerifiers() {
        return verifiers;
    }

    @Override
    public List<Anchor> getAnchors() {
        return allAnchors;
    }

}

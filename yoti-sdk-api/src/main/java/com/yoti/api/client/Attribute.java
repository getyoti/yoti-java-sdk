package com.yoti.api.client;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.Collections;
import java.util.List;

/**
 * Represents a generic, typed key/value pair, a basic building block of
 * {@link Profile} instances.
 */
public class Attribute<T> {

    private final String name;
    private final T value;
    private final List<Anchor> sources;
    private final List<Anchor> verifiers;
    private final List<Anchor> allAnchors;

    public Attribute(String name, T value) {
        this(name, value, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public Attribute(String name, T value, List<Anchor> sources, List<Anchor> verifiers, List<Anchor> allAnchors) {
        this.name = name;
        this.value = value;
        this.sources = notNull(sources, "sources");
        this.verifiers = notNull(verifiers, "verifiers");
        this.allAnchors = notNull(allAnchors, "allAnchors");
    }

    /**
     * The name of the attribute
     * @return name of the attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Value of the attribute
     *
     * @return attribute value
     */
    public T getValue() {
        return value;
    }

    /**
     * Source {@link Anchor}s identify how and when an attribute value was acquired
     *
     * @return List of sources for the attribute value
     */
    public List<Anchor> getSources() {
        return sources;
    }

    /**
     * Verifier {@link Anchor}s identify how and when an attribute value was verified by another provider
     *
     * @return List of verifiers for the attribute value
     */
    public List<Anchor> getVerifiers() {
        return verifiers;
    }

    /**
     * All known {@link Anchor}s for this attribute.  Includes SOURCE and VERIFIER Anchors, in future may provide other types too
     *
     * @return All anchors returned with the attribute
     */
    public List<Anchor> getAnchors() {
        return allAnchors;
    }

}

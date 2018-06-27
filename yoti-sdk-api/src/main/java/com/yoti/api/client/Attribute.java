package com.yoti.api.client;

import java.util.List;

/**
 * Represents a generic, typed key/value pair, a basic building block of
 * {@link Profile} instances.
 *
 */
public interface Attribute<T> {

    /**
     * The name of the attribute
     * @return name of the attribute
     */
    String getName();

    /**
     * Value of the attribute
     *
     * @return attribute value
     */
    T getValue();

    /**
     * Source {@link Anchor}s identify how and when an attribute value was acquired
     *
     * @return List of sources for the attribute value
     */
    List<Anchor> getSources();

    /**
     * Verifier {@link Anchor}s identify how and when an attribute value was verified by another provider
     *
     * @return List of verifiers for the attribute value
     */
    List<Anchor> getVerifiers();

    /**
     * All known {@link Anchor}s for this attribute.  Includes SOURCE and VERIFIER Anchors, in future may provide other types too
     *
     * @return All anchors returned with the attribute
     */
    List<Anchor> getAnchors();

}

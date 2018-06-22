package com.yoti.api.client;

import java.util.List;

/**
 * Represents a generic, typed key/value pair, a basic building block of
 * {@link Profile} instances.
 *
 */
public interface Attribute<T> {

    String getName();

    T getValue();

    List<Anchor> getSources();

    List<Anchor> getVerifiers();

    List<Anchor> getAnchors();

}

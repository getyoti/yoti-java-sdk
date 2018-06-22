package com.yoti.api.client;

import java.util.Set;

/**
 * Represents a generic, typed key/value pair, a basic building block of
 * {@link Profile} instances.
 *
 */
public interface Attribute<T> {

    String getName();

    T getValue();

    Set<String> getSources();

    Set<String> getVerifiers();
}

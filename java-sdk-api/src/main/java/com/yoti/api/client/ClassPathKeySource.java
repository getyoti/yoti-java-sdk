package com.yoti.api.client;

import java.io.InputStream;

/**
 * Responsible for getting key pairs from a given classpath location.
 *
 */
public final class ClassPathKeySource extends AutoClosingKeyPairSource {
    private final InputStream stream;
    private final String asString;

    public static ClassPathKeySource fromClasspath(String location) throws InitialisationException {
        return new ClassPathKeySource(location);
    }

    private ClassPathKeySource(String location) throws InitialisationException {
        this.asString = "classpath: " + location;
        stream = getClass().getClassLoader().getResourceAsStream(location);
        if (stream == null) {
            throw new InitialisationException("No resource found at classpath location " + location);
        }
    }

    @Override
    protected InputStream getStream() {
        return stream;
    }

    @Override
    public String toString() {
        return asString;
    }
}

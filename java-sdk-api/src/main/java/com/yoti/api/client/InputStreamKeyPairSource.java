package com.yoti.api.client;

import java.io.InputStream;

/**
 * Responsible for getting key pairs from am existing InputStream instance.
 * Streams will not be closed upon completion.
 *
 */
public class InputStreamKeyPairSource extends AutoClosingKeyPairSource {
    private final InputStream stream;

    public static InputStreamKeyPairSource fromStream(InputStream inputStream) {
        return new InputStreamKeyPairSource(inputStream);
    }

    private InputStreamKeyPairSource(InputStream inputStream) {
        stream = inputStream;
    }

    @Override
    protected InputStream getStream() {
        return stream;
    }

    @Override
    protected boolean shouldClose() {
        // Do not close stream as it's an external one.
        return false;
    }

    @Override
    public String toString() {
        return "a stream";
    }
}

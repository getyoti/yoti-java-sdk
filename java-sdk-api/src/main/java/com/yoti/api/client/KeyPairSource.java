package com.yoti.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

/**
 * <p>
 * Responsible for getting key pairs from a source. It parses the stream using
 * the {@link StreamVisitor}. Have a look at the implementation to see what
 * sources are supported or feel free to implement your own.
 * </p>
 * <p>
 * Implementations should only handle the underlying stream, leaving actual
 * parsing to the supplied StreamVisitor instance.
 * </p>
 *
 */
public interface KeyPairSource {
    /**
     * Parse stream held using the StreamVisitor and return the KeyPair
     * instance.
     *
     * @param streamVisitor
     *            visitor handling the actual parsing from the stream
     * @return key pair
     * @throws IOException
     *             signals issues during stream operations
     * @throws InitialisationException if the key pair could not be loaded
     */
    KeyPair getFromStream(StreamVisitor streamVisitor) throws IOException, InitialisationException;

    public static interface StreamVisitor {
        KeyPair accept(InputStream stream) throws IOException, InitialisationException;
    }
}

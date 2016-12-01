package com.yoti.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

/**
 * Key pair source handling closing of streams automatically.
 *
 */
abstract class AutoClosingKeyPairSource implements KeyPairSource {
    private boolean closed = false;

    @Override
    public final KeyPair getFromStream(StreamVisitor streamVisitor) throws IOException, InitialisationException {
        KeyPair keyPair = null;
        if (closed) {
            throw new IllegalStateException("Stream already closed");
        }

        try {
            keyPair = streamVisitor.accept(getStream());
        } finally {
            if (shouldClose()) {
                try {
                    getStream().close();
                } catch (IOException ioe) {

                }
                closed = true;
            }
        }
        return keyPair;
    }

    /**
     * Return stream held by the source. Expected to return the same instance
     * every time.
     *
     * @return stream instance
     */
    protected abstract InputStream getStream();

    /**
     * Override this to prevent the stream from closing.
     *
     * @return true if the stream should not be closed
     */
    protected boolean shouldClose() {
        return true;
    }
}

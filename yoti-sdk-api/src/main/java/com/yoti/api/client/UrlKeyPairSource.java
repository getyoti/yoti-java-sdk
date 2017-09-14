package com.yoti.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Responsible for getting key pairs from a URL.
 *
 */
public final class UrlKeyPairSource extends AutoClosingKeyPairSource {
    private final InputStream stream;

    public static UrlKeyPairSource fromUrl(URL url) throws InitialisationException {
        return new UrlKeyPairSource(url);
    }

    private UrlKeyPairSource(URL url) throws InitialisationException {
        try {
            stream = url.openStream();
        } catch (IOException e) {
            throw new InitialisationException("Cannot open keypair from URL: " + url, e);
        }
    }

    @Override
    protected InputStream getStream() {
        return stream;
    }
}

package com.yoti.api.spring;

import com.yoti.api.client.KeyPairSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

public class SpringResourceKeyPairSource implements KeyPairSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringResourceKeyPairSource.class);

    private final Resource resource;

    public SpringResourceKeyPairSource(final Resource resource) {
        this.resource = resource;
    }

    @Override
    public KeyPair getFromStream(final StreamVisitor streamVisitor) throws IOException {
        InputStream in = null;
        try {
            LOGGER.debug("Found key pair source {}", resource);
            in = resource.getInputStream();
            return streamVisitor.accept(in);
        } finally {
            closeQuietly(in);
        }
    }

    private void closeQuietly(final InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (final IOException ioe) {
                LOGGER.error("Unable to close key pair source input stream.", ioe);
            }
        }
    }

    @Override
    public String toString() {
        return "SpringResourceKeyPairSource{" +
                "resource=" + resource +
                '}';
    }
}

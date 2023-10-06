package com.yoti.api.spring;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

import com.yoti.api.client.KeyPairSource;

import org.springframework.core.io.Resource;

public class SpringResourceKeyPairSource implements KeyPairSource {

    private final Resource resource;

    public SpringResourceKeyPairSource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public KeyPair getFromStream(StreamVisitor stream) throws IOException {
        try (InputStream is = resource.getInputStream()) {
            return stream.accept(is);
        }
    }

}

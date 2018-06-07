package com.yoti.api.client.spi.remote.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuietCloseable<T extends AutoCloseable> implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(QuietCloseable.class);

    private final T autoCloseable;

    public QuietCloseable(T autoCloseable) {
        this.autoCloseable = autoCloseable;
    }

    public T get() {
        return autoCloseable;
    }

    @Override
    public void close() {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            LOG.error("Failed to close " + autoCloseable.getClass().getCanonicalName(), e);
        }
    }

}

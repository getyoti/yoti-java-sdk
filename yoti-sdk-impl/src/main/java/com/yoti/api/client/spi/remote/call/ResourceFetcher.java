package com.yoti.api.client.spi.remote.call;

import java.io.IOException;
import java.util.Map;

interface ResourceFetcher {
    /**
     * Should not return null.
     */
    <T> T fetchResource(UrlConnector urlConnector, Map<String, String> headers, Class<T> resourceClass)
            throws ResourceException, IOException;
}

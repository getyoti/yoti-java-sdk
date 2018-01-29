package com.yoti.api.client.spi.remote.call;

import java.io.IOException;
import java.util.Map;

public interface ResourceFetcher {

    <T> T fetchResource(UrlConnector urlConnector, Map<String, String> headers, Class<T> resourceClass) throws ResourceException, IOException;

    <T> T postResource(UrlConnector urlConnector, byte[] body, Map<String, String> headers, Class<T> resourceClass) throws ResourceException, IOException;

}

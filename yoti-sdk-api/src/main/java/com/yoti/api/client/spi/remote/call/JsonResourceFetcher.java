package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonResourceFetcher implements ResourceFetcher {

    private final ObjectMapper objectMapper;
    private final RawResourceFetcher rawResourceFetcher;

    public static JsonResourceFetcher newInstance() {
        return JsonResourceFetcher.newInstance(new RawResourceFetcher());
    }

    public static JsonResourceFetcher newInstance(RawResourceFetcher rawResourceFetcher) {
        return new JsonResourceFetcher(new ObjectMapper(), rawResourceFetcher);
    }

    private JsonResourceFetcher(ObjectMapper objectMapper,
            RawResourceFetcher rawResourceFetcher) {
        this.objectMapper = objectMapper;
        this.rawResourceFetcher = rawResourceFetcher;
    }

    @Override
    @Deprecated
    public <T> T fetchResource(UrlConnector urlConnector, Map<String, String> headers, Class<T> resourceClass) throws ResourceException, IOException {
        return doRequest(urlConnector, HTTP_GET, null, headers, resourceClass);
    }

    @Override
    @Deprecated
    public <T> T postResource(UrlConnector urlConnector, byte[] body, Map<String, String> headers, Class<T> resourceClass)
            throws ResourceException, IOException {
        return doRequest(urlConnector, HTTP_POST, body, headers, resourceClass);
    }

    @Deprecated
    public <T> T doRequest(UrlConnector urlConnector, String httpMethod, byte[] body, Map<String, String> headers, Class<T> resourceClass) throws ResourceException, IOException {
        SignedRequestResponse signedRequestResponse = rawResourceFetcher.doRequest(urlConnector, httpMethod, body, headers);
        return objectMapper.readValue(signedRequestResponse.getResponseBody(), resourceClass);
    }

    <T> T doRequest(SignedRequest signedRequest, Class<T> resourceClass) throws ResourceException, IOException {
        SignedRequestResponse signedRequestResponse = rawResourceFetcher.doRequest(signedRequest);
        return objectMapper.readValue(signedRequestResponse.getResponseBody(), resourceClass);
    }

}

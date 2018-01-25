package com.yoti.api.client.spi.remote.call;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Scanner;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static java.net.HttpURLConnection.HTTP_OK;

public final class JsonResourceFetcher implements ResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(JsonResourceFetcher.class);

    private final ObjectMapper objectMapper;

    public static JsonResourceFetcher newInstance() {
        return new JsonResourceFetcher(new ObjectMapper());
    }

    JsonResourceFetcher(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T fetchResource(UrlConnector urlConnector, Map<String, String> headers, Class<T> resourceClass) throws ResourceException, IOException {
        HttpURLConnection httpUrlConnection = openConnection(urlConnector, HTTP_GET, headers);
        return parseResponse(httpUrlConnection, resourceClass);
    }

    @Override
    public <T> T postResource(UrlConnector urlConnector, byte[] body, Map<String, String> headers, Class<T> resourceClass)
            throws ResourceException, IOException {

        HttpURLConnection httpUrlConnection = openConnection(urlConnector, HTTP_POST, headers);
        sendBody(body, httpUrlConnection);
        return parseResponse(httpUrlConnection, resourceClass);
    }

    private HttpURLConnection openConnection(UrlConnector urlConnector, String httpMethod, Map<String, String> headers) throws IOException {
        LOG.debug("Connecting to: " + urlConnector.getUrlString());
        HttpURLConnection httpUrlConnection = urlConnector.getHttpUrlConnection();
        httpUrlConnection.setRequestMethod(httpMethod);
        setHeaders(headers, httpUrlConnection);
        return httpUrlConnection;
    }

    private void setHeaders(Map<String, String> headers, HttpURLConnection con) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private void sendBody(byte[] body, HttpURLConnection httpUrlConnection) throws IOException {
        if (body != null) {
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.getOutputStream().write(body);
            httpUrlConnection.getOutputStream().close();
        }
    }

    private <T> T parseResponse(HttpURLConnection httpUrlConnection, Class<T> resourceClass) throws ResourceException, IOException {
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            String responseBody = readAll(httpUrlConnection.getErrorStream());
            throw new ResourceException(responseCode, responseBody);
        }
        return parseResource(httpUrlConnection.getInputStream(), resourceClass);
    }

    private String readAll(InputStream inputStream) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputStream, DEFAULT_CHARSET).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private <T> T parseResource(InputStream inputStream, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    LOG.error("Cannot close connection to service.");
                }
            }
        }
    }

}

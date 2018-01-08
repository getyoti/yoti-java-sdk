package com.yoti.api.client.spi.remote.call;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Scanner;

import static java.net.HttpURLConnection.HTTP_OK;

final class JsonResourceFetcher implements ResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(JsonResourceFetcher.class);
    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private final ObjectMapper objectMapper;

    public static JsonResourceFetcher createInstance() {
        return new JsonResourceFetcher(new ObjectMapper());
    }

    JsonResourceFetcher(ObjectMapper json_mapper) {
        objectMapper = json_mapper;
    }

    @Override
    public <T> T fetchResource(UrlConnector urlConnector, Map<String, String> headers, Class<T> resourceClass) throws ResourceException, IOException {
        HttpURLConnection httpUrlConnection = openConnection(urlConnector, HTTP_GET, headers);
        return parseResponse(httpUrlConnection, resourceClass);
    }

    @Override
    public <T> T postResource(UrlConnector urlConnector, Object body, Map<String, String> headers, Class<T> resourceClass)
            throws ResourceException, IOException {

        HttpURLConnection httpUrlConnection = openConnection(urlConnector, HTTP_POST, headers);
        sendBody(body, httpUrlConnection);
        return parseResponse(httpUrlConnection, resourceClass);
    }

    private HttpURLConnection openConnection(UrlConnector urlConnector, String httpMethod, Map<String, String> headers) throws IOException {
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

    private void sendBody(Object body, HttpURLConnection httpUrlConnection) throws IOException {
        if (body != null) {
            String jsonBody = objectMapper.writeValueAsString(body);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.getOutputStream().write(jsonBody.getBytes(DEFAULT_CHARSET));
            httpUrlConnection.getOutputStream().close();
        }
    }

    private <T> T parseResponse(HttpURLConnection httpUrlConnection, Class<T> resourceClass) throws ResourceException, IOException {
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode != HTTP_OK) {
            String responseBody = readAll(httpUrlConnection.getInputStream());
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

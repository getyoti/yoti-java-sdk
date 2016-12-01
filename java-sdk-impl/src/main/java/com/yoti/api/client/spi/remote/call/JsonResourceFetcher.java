package com.yoti.api.client.spi.remote.call;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

final class JsonResourceFetcher implements ResourceFetcher {
    private static final Logger LOG = LoggerFactory.getLogger(JsonResourceFetcher.class);
    private static final String HTTP_GET_METHOD = "GET";
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final String DEFAULT_CHARSET = "UTF-8";

    @Override
    public <T> T fetchResource(UrlConnector urlConnector, Map<String, String> headers, Class<T> resourceClass)
            throws ResourceException, IOException {

        HttpURLConnection con = urlConnector.getHttpUrlConnection();

        con.setRequestMethod(HTTP_GET_METHOD);
        setHeaders(headers, con);

        int responseCode = con.getResponseCode();
        if (responseCode != HTTP_OK) {
            String body = readAll(con.getInputStream());
            throw new ResourceException(responseCode, body);
        }

        return parseResource(con.getInputStream(), resourceClass);
    }

    private void setHeaders(Map<String, String> headers, HttpURLConnection con) {
        for (Map.Entry<String, String> e : headers.entrySet()) {
            con.setRequestProperty(e.getKey(), e.getValue());
        }
    }

    private String readAll(InputStream inputStream) {
        String result = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputStream, DEFAULT_CHARSET).useDelimiter("\\A");
            result = scanner.hasNext() ? scanner.next() : "";
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return result;
    }

    private <T> T parseResource(InputStream inputStream, Class<T> clazz) throws IOException {
        T result = null;
        try {
            result = JSON_MAPPER.readValue(inputStream, clazz);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    LOG.error("Cannot close connection to service.");
                }
            }
        }
        return result;
    }

}

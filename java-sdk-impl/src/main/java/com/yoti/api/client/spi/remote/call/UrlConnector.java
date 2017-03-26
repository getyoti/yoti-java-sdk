package com.yoti.api.client.spi.remote.call;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnector {

    private static final String DEFAULT_YOTI_CONNECT_TIMEOUT = "0";
    private static final String DEFAULT_YOTI_READ_TIMEOUT = "0";
    private static final String PROPERTY_YOTI_CONNECT_TIMEOUT = "yoti.client.connect.timeout.ms";
    private static final String PROPERTY_YOTI_READ_TIMEOUT = "yoti.client.read.timeout.ms";

    private final String urlString;
    private final int connectTimeout;
    private final int readTimeout;

    private UrlConnector(final String urlString) {
        this.urlString = urlString;
        this.connectTimeout = getPropertyOrDefaultAsInt(PROPERTY_YOTI_CONNECT_TIMEOUT, DEFAULT_YOTI_CONNECT_TIMEOUT);
        this.readTimeout = getPropertyOrDefaultAsInt(PROPERTY_YOTI_READ_TIMEOUT, DEFAULT_YOTI_READ_TIMEOUT);
    }

    public static UrlConnector get(final String urlString) {
        return new UrlConnector(urlString);
    }

    public HttpURLConnection getHttpUrlConnection() throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        configureTimeouts(connection);
        return connection;
    }

    String getUrlString() {
        return urlString;
    }

    private void configureTimeouts(final HttpURLConnection connection) {
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
    }

    private int getPropertyOrDefaultAsInt(final String propertyKey, final String defaultValue) {
        final String value = System.getProperty(propertyKey, defaultValue);
        return Integer.parseInt(value);
    }
}

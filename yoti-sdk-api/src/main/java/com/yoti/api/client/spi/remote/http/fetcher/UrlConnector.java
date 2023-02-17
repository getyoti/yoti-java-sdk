package com.yoti.api.client.spi.remote.http.fetcher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnector {

    private final String urlString;
    private final int connectTimeout;
    private final int readTimeout;

    private UrlConnector(String urlString) {
        this.urlString = urlString;
        connectTimeout = parse(Config.PROPERTY_YOTI_CONNECT_TIMEOUT, Config.DEFAULT_YOTI_CONNECT_TIMEOUT);
        readTimeout = parse(Config.PROPERTY_YOTI_READ_TIMEOUT, Config.DEFAULT_YOTI_READ_TIMEOUT);
    }

    public static UrlConnector get(String urlString) {
        return new UrlConnector(urlString);
    }

    public String getUrlString() {
        return urlString;
    }

    public HttpURLConnection getHttpUrlConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        configureTimeouts(connection);
        return connection;
    }

    private void configureTimeouts(HttpURLConnection connection) {
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
    }

    private int parse(String propertyKey, String defaultValue) {
        return Integer.parseInt(System.getProperty(propertyKey, defaultValue));
    }

    private static final class Config {

        private static final String DEFAULT_YOTI_CONNECT_TIMEOUT = "0";
        private static final String DEFAULT_YOTI_READ_TIMEOUT = "0";
        private static final String PROPERTY_YOTI_CONNECT_TIMEOUT = "yoti.client.connect.timeout.ms";
        private static final String PROPERTY_YOTI_READ_TIMEOUT = "yoti.client.read.timeout.ms";

    }

}

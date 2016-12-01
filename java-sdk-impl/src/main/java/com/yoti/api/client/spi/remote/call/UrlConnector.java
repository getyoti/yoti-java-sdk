package com.yoti.api.client.spi.remote.call;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnector {
    private final String urlString;

    private UrlConnector(String urlString) {
        this.urlString = urlString;
    }

    public static UrlConnector get(String urlString) {
        return new UrlConnector(urlString);
    }

    public HttpURLConnection getHttpUrlConnection() throws IOException {
        return (HttpURLConnection) new URL(urlString).openConnection();
    }

    String getUrlString() {
        return urlString;
    }
}

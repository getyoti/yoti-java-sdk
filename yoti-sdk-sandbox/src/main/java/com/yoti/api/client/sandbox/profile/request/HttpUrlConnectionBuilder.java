package com.yoti.api.client.sandbox.profile.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUrlConnectionBuilder {

    private String schemeAndAuthority;
    private String path;
    private String methodName;
    private Map<String, String> queryParameters;
    private Map<String, String> headers;

    public static HttpUrlConnectionBuilder newBuilder() {
        return new HttpUrlConnectionBuilder();
    }

    public HttpUrlConnectionBuilder schemeAndAuthority(String scheme) {
        this.schemeAndAuthority = scheme;
        return this;
    }

    public HttpUrlConnectionBuilder path(String path) {
        this.path = path;
        return this;
    }

    public HttpUrlConnectionBuilder methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public HttpUrlConnectionBuilder queryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
        return this;
    }

    public HttpUrlConnectionBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public HttpURLConnection build() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(
                schemeAndAuthority + path + buildQueryParameters(queryParameters)).openConnection();
        connection.setRequestMethod(methodName);
        setHeaders(headers, connection);

        return connection;
    }

    private void setHeaders(Map<String, String> headers, HttpURLConnection httpURLConnection) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private String buildQueryParameters(Map<String, String> queryParameters) {
        StringBuilder queryParametersResult = new StringBuilder("");
        if (queryParameters != null) {
            queryParametersResult.append("?");
            for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                queryParametersResult.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
            queryParametersResult = new StringBuilder(queryParametersResult.substring(0,
                    queryParametersResult.length() - 1));
        }
        return queryParametersResult.toString();
    }

}

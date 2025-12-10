package com.yoti.auth;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.util.QuietCloseable;

/**
 * Internal use only.
 * <p>
 * The {@link FormRequestClient} is used for performing an application/x-www-form-urlencoded
 * HTTP request using base Java libraries only.
 */
final class FormRequestClient {

    byte[] performRequest(URL url, String method, Map<String, String> formParams) throws IOException, ResourceException {
        byte[] postData = getData(formParams);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
        connection.setRequestProperty("charset", StandardCharsets.UTF_8.toString());
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(false);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postData);
        }

        return parseResponse(connection);
    }

    private byte[] getData(Map<String, String> params) {
        return params.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"))
                .getBytes(StandardCharsets.UTF_8);
    }

    private static String encode(String v) {
        try {
            return URLEncoder.encode(v, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] parseResponse(HttpURLConnection httpUrlConnection) throws ResourceException, IOException {
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            byte[] responseBody = readBody(httpUrlConnection);
            throw new ResourceException(responseCode, httpUrlConnection.getResponseMessage(), new String(responseBody));
        }
        return readBody(httpUrlConnection);
    }

    private byte[] readBody(HttpURLConnection httpURLConnection) throws IOException {
        try (QuietCloseable<InputStream> inputStream = new QuietCloseable<>(httpURLConnection.getInputStream())) {
            return readChunked(inputStream.get());
        }
    }

    private byte[] readChunked(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byteChunk = new byte[4096];
        int n;
        while ((n = inputStream.read(byteChunk)) > 0) {
            byteArrayOutputStream.write(byteChunk, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

}

package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Scanner;

import com.yoti.api.client.spi.remote.util.QuietCloseable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RawResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(RawResourceFetcher.class);

    private static Scanner createScanner(InputStream inputStream) {
        return new Scanner(inputStream, DEFAULT_CHARSET).useDelimiter("\\A");
    }

    public YotiHttpResponse doRequest(YotiHttpRequest yotiHttpRequest) throws IOException, ResourceException {
        UrlConnector urlConnector = UrlConnector.get(yotiHttpRequest.getUri().toString());
        return doRequest(urlConnector, yotiHttpRequest.getMethod(), yotiHttpRequest.getData(), yotiHttpRequest.getHeaders());
    }

    public YotiHttpResponse doRequest(UrlConnector urlConnector, String method, byte[] data, Map<String, String> headers)
            throws IOException, ResourceException {
        HttpURLConnection httpUrlConnection = openConnection(urlConnector, method, headers);
        sendBody(data, httpUrlConnection);
        return parseResponse(httpUrlConnection);
    }

    /**
     * Open a connection to the specified host, using the httpMethod and headers.
     *
     * @param urlConnector the {@link UrlConnector}
     * @param httpMethod   the HTTP Method
     * @param headers      {@link Map} of headers
     * @return {@link HttpURLConnection} the url connection
     * @throws IOException
     */
    private HttpURLConnection openConnection(UrlConnector urlConnector, String httpMethod, Map<String, String> headers) throws IOException {
        LOG.debug("Connecting to: '{}'", urlConnector.getUrlString());
        HttpURLConnection httpUrlConnection = urlConnector.getHttpUrlConnection();
        httpUrlConnection.setRequestMethod(httpMethod);
        setHeaders(headers, httpUrlConnection);
        return httpUrlConnection;
    }

    /**
     * Sets the request properties on a {@link HttpURLConnection}
     * using supplied {@link Map}
     *
     * @param headers the request headers
     * @param con     the connection
     */
    private void setHeaders(Map<String, String> headers, HttpURLConnection con) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Sends request body to the supplied {@link HttpURLConnection}
     *
     * @param body              the request body
     * @param httpUrlConnection the http connection
     * @throws IOException
     */
    private void sendBody(byte[] body, HttpURLConnection httpUrlConnection) throws IOException {
        if (body != null) {
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.getOutputStream().write(body);
            httpUrlConnection.getOutputStream().close();
        }
    }

    /**
     * Parse the response from the {@link HttpURLConnection}
     *
     * @param httpUrlConnection the url connection
     * @return the response
     * @throws ResourceException
     * @throws IOException
     */
    private YotiHttpResponse parseResponse(HttpURLConnection httpUrlConnection) throws ResourceException, IOException {
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            String responseBody = readError(httpUrlConnection);
            throw new ResourceException(responseCode, httpUrlConnection.getResponseMessage(), responseBody);
        }
        return new YotiHttpResponse(responseCode, readBody(httpUrlConnection), httpUrlConnection.getHeaderFields());
    }

    /**
     * Read the error from the {@link HttpURLConnection} if an error
     * HTTP status code has been returned.
     *
     * @param httpURLConnection the url connection
     * @return the error string
     */
    private String readError(HttpURLConnection httpURLConnection) {
        try (QuietCloseable<Scanner> scanner = new QuietCloseable<>(createScanner(httpURLConnection.getErrorStream()))) {
            return scanner.get().hasNext() ? scanner.get().next() : "";
        }
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

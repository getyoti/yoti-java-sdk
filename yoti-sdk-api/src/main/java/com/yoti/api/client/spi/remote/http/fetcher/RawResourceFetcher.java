package com.yoti.api.client.spi.remote.http.fetcher;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Scanner;

import com.yoti.api.client.spi.remote.http.HttpMethod;
import com.yoti.api.client.spi.remote.http.Request;
import com.yoti.api.client.spi.remote.http.ResourceException;
import com.yoti.api.client.spi.remote.http.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(RawResourceFetcher.class);

    public static RawResourceFetcher newInstance() {
        return new RawResourceFetcher();
    }

    private RawResourceFetcher() { }

    public Response doRequest(Request request) throws IOException, ResourceException {
        UrlConnector urlConnector = UrlConnector.get(request.uri().toString());
        return doRequest(
                urlConnector,
                request.method(),
                request.data(),
                request.headers()
        );
    }

    public Response doRequest(UrlConnector urlConnector, HttpMethod method, byte[] data, Map<String, String> headers)
            throws IOException, ResourceException {
        HttpURLConnection conn = openConnection(urlConnector, method, headers);
        sendBody(data, conn);
        return parseResponse(conn);
    }

    /**
     * Open a connection to the specified host, using the httpMethod and headers.
     *
     * @param urlConnector the {@link UrlConnector}
     * @param httpMethod   the HTTP Method
     * @param headers      {@link Map} of headers
     * @return {@link HttpURLConnection} the url connection
     */
    private HttpURLConnection openConnection(UrlConnector urlConnector, HttpMethod method, Map<String, String> headers)
            throws IOException {
        LOG.debug("Connecting to: '{}'", urlConnector.getUrlString());

        HttpURLConnection conn = urlConnector.getHttpUrlConnection();
        conn.setRequestMethod(method.name());

        setHeaders(headers, conn);
        return conn;
    }

    /**
     * Sets the request properties on a {@link HttpURLConnection}
     * using supplied {@link Map}
     *
     * @param headers the request headers
     * @param conn     the connection
     */
    private void setHeaders(Map<String, String> headers, HttpURLConnection conn) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Sends request body to the supplied {@link HttpURLConnection}
     *
     * @param body              the request body
     * @param conn the http connection
     */
    private void sendBody(byte[] body, HttpURLConnection conn) throws IOException {
        if (body != null) {
            conn.setDoOutput(true);
            conn.getOutputStream().write(body);
            conn.getOutputStream().close();
        }
    }

    /**
     * Parse the response from the {@link HttpURLConnection}
     *
     * @param conn the url connection
     * @return the response
     */
    private Response parseResponse(HttpURLConnection conn) throws ResourceException, IOException {
        int responseCode = conn.getResponseCode();
        if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            String body = readError(conn);
            throw new ResourceException(responseCode, conn.getResponseMessage(), body);
        }
        return new Response(responseCode, readBody(conn), conn.getHeaderFields());
    }

    /**
     * Read the error from the {@link HttpURLConnection} if an error
     * HTTP status code has been returned.
     *
     * @param conn the url connection
     * @return the error string
     */
    private String readError(HttpURLConnection conn) {
        try (QuietCloseable<Scanner> scanner = new QuietCloseable<>(createScanner(conn.getErrorStream()))) {
            return scanner.get().hasNext() ? scanner.get().next() : "";
        }
    }

    private static Scanner createScanner(InputStream is) {
        return new Scanner(is, DEFAULT_CHARSET).useDelimiter("\\A");
    }

    private byte[] readBody(HttpURLConnection conn) throws IOException {
        try (QuietCloseable<InputStream> is = new QuietCloseable<>(conn.getInputStream())) {
            return readChunked(is.get());
        }
    }

    private byte[] readChunked(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byteChunk = new byte[4096];
        int n;
        while ((n = is.read(byteChunk)) > 0) {
            byteArrayOutputStream.write(byteChunk, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

}

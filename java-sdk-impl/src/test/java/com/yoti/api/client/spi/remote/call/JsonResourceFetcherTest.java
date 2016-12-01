package com.yoti.api.client.spi.remote.call;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonResourceFetcherTest {
    @Test
    public void shouldReturnResource() throws IOException, ResourceException {
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getResponseCode()).thenReturn(HTTP_OK);
        when(connection.getInputStream()).thenReturn(stream("{ \"x\":\"test-response\"}"));

        UrlConnector urlConnector = mock(UrlConnector.class);
        when(urlConnector.getHttpUrlConnection()).thenReturn(connection);

        JsonResourceFetcher fetcher = new JsonResourceFetcher();
        TestClass test = fetcher.fetchResource(urlConnector, setupHeaders(), TestClass.class);

        assertNotNull(test);
        assertEquals("test-response", test.field);

        verify(connection).setRequestMethod("GET");
        verify(connection).setRequestProperty("test-header", "test-value");
    }

    @Test
    public void shouldFailForNonOkStatusCode() throws IOException, ResourceException {
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("TEST".getBytes()));

        UrlConnector urlConnector = mock(UrlConnector.class);
        when(urlConnector.getHttpUrlConnection()).thenReturn(connection);

        JsonResourceFetcher fetcher = new JsonResourceFetcher();
        try {
            fetcher.fetchResource(urlConnector, setupHeaders(), TestClass.class);
            fail("ResourceException expected");
        } catch (ResourceException re) {
            verify(connection, times(1)).getInputStream();
        }
    }

    @Test
    public void shouldCloseConnectionWhenJsonError() throws IOException, ResourceException {
        InputStream stream = mock(InputStream.class);
        when(stream.read()).thenThrow(new IOException());

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getResponseCode()).thenReturn(HTTP_OK);
        when(connection.getInputStream()).thenReturn(stream);

        UrlConnector urlConnector = mock(UrlConnector.class);
        when(urlConnector.getHttpUrlConnection()).thenReturn(connection);

        JsonResourceFetcher fetcher = new JsonResourceFetcher();
        try {
            fetcher.fetchResource(urlConnector, setupHeaders(), TestClass.class);
            fail("IOException expected");
        } catch (IOException ioe) {
            // ObjectMapper closes the stream first, hence the 2
            verify(stream, times(2)).close();
        }
    }

    private InputStream stream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }

    private Map<String, String> setupHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("test-header", "test-value");
        return headers;
    }

    public static class TestClass {
        @JsonProperty("x")
        private String field;
    }
}

package com.yoti.api.client.spi.remote.call;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonResourceFetcherTest {

    private static final String ERROR_BODY = "errorBody";
    private static final String TEST_HEADER_KEY = "testHeader";
    private static final String TEST_HEADER_VALUE = "testValue";
    private static final Map<String, String> TEST_HEADERS = new HashMap<String, String>();

    @InjectMocks JsonResourceFetcher testObj;

    @Mock ObjectMapper objectMapperMock;

    @Mock(answer = RETURNS_DEEP_STUBS) HttpURLConnection httpURLConnectionMock;
    @Mock UrlConnector urlConnectorMock;
    @Mock InputStream inputStreamMock;
    byte[] requestBody = new byte[]{};
    Object parsedResponse = new Object();

    @BeforeClass
    public static void classSetup() {
        TEST_HEADERS.put(TEST_HEADER_KEY, TEST_HEADER_VALUE);
    }

    @Test
    public void fetchResource_shouldReturnResource() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);
        when(objectMapperMock.readValue(inputStreamMock, Object.class)).thenReturn(parsedResponse);

        Object result = testObj.fetchResource(urlConnectorMock, TEST_HEADERS, Object.class);

        verify(httpURLConnectionMock).setRequestMethod("GET");
        verify(httpURLConnectionMock).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertSame(parsedResponse, result);
    }

    @Test
    public void fetchResource_shouldFailForNonOkStatusCode() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);
        when(httpURLConnectionMock.getErrorStream()).thenReturn(stream(ERROR_BODY));
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);

        try {
            testObj.fetchResource(urlConnectorMock, TEST_HEADERS, Object.class);
            fail("ResourceException expected");
        } catch (ResourceException re) {
            assertEquals(HTTP_BAD_REQUEST, re.getResponseCode());
            assertEquals(ERROR_BODY, re.getResponseBody());
        }
    }

    @Test
    public void fetchResource_shouldCloseConnectionAfterParsingError() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);
        IOException ioException = new IOException();
        when(objectMapperMock.readValue(inputStreamMock, Object.class)).thenThrow(ioException);

        try {
            testObj.fetchResource(urlConnectorMock, TEST_HEADERS, Object.class);
            testObj.fetchResource(urlConnectorMock, null, Object.class);
            fail("IOException expected");
        } catch (IOException ioe) {
            verify(inputStreamMock).close();
        }
    }

    @Test
    public void postResource_shouldSendBodyAndReturnResponse() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);
        when(objectMapperMock.readValue(inputStreamMock, Object.class)).thenReturn(parsedResponse);

        Object result = testObj.postResource(urlConnectorMock, requestBody, TEST_HEADERS, Object.class);

        verify(httpURLConnectionMock).setRequestMethod("POST");
        verify(httpURLConnectionMock).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        verify(httpURLConnectionMock.getOutputStream()).write(requestBody);
        assertSame(parsedResponse, result);
    }

    @Test
    public void postResource_shouldFailForNonOkStatusCode() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);
        when(httpURLConnectionMock.getErrorStream()).thenReturn(stream(ERROR_BODY));
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);

        try {
            testObj.postResource(urlConnectorMock, null, null, Object.class);
            fail("ResourceException expected");
        } catch (ResourceException re) {
            assertEquals(HTTP_BAD_REQUEST, re.getResponseCode());
            assertEquals(ERROR_BODY, re.getResponseBody());
        }
    }

    @Test
    public void postResource_shouldCloseConnectionAfterParsingError() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);
        IOException ioException = new IOException();
        when(objectMapperMock.readValue(inputStreamMock, Object.class)).thenThrow(ioException);

        try {
            testObj.postResource(urlConnectorMock, null, null, Object.class);
            fail("IOException expected");
        } catch (IOException ioe) {
            verify(inputStreamMock).close();
        }
    }

    private InputStream stream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }

}

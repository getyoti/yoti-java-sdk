package com.yoti.api.client.spi.remote.call;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RawResourceFetcherTest {

    private static final String ERROR_BODY = "errorBody";
    private static final String TEST_HEADER_KEY = "testHeader";
    private static final String TEST_HEADER_VALUE = "testValue";
    private static final Map<String, String> TEST_HEADERS = new HashMap<>();

    @Spy @InjectMocks RawResourceFetcher testObj;

    @Mock(answer = RETURNS_DEEP_STUBS) HttpURLConnection httpURLConnectionMock;
    @Mock UrlConnector urlConnectorMock;

    InputStream inputStreamSpy;
    InputStream errorStreamSpy;

    byte[] requestBody = new byte[]{5, 6, 7, 8};
    byte[] responseBody = new byte[]{1, 2, 3, 4};

    @BeforeClass
    public static void classSetup() {
        TEST_HEADERS.put(TEST_HEADER_KEY, TEST_HEADER_VALUE);
    }

    @Before
    public void setUp() throws Exception {
        inputStreamSpy = spy(new ByteArrayInputStream(responseBody));
        errorStreamSpy = spy(new ByteArrayInputStream(ERROR_BODY.getBytes()));
        when(urlConnectorMock.getHttpUrlConnection()).thenReturn(httpURLConnectionMock);
        when(httpURLConnectionMock.getErrorStream()).thenReturn(errorStreamSpy);
    }

    @Test
    public void doRequest_shouldReturnByteArray() throws Exception {
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamSpy);
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);

        Response result = testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);

        verify(httpURLConnectionMock).setRequestMethod("GET");
        verify(httpURLConnectionMock).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertArrayEquals(responseBody, result.body());
        verify(inputStreamSpy).close();
    }

    @Test
    public void chunkRead_shouldHandleInputStreamSmallerThanDefaultChunkSize() throws Exception {
        byte[] response = createByteArrayOfSize(40);
        InputStream smallerSizeByteArray = spy(new ByteArrayInputStream(response));

        when(httpURLConnectionMock.getInputStream()).thenReturn(smallerSizeByteArray);
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);

        Response result = testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);

        verify(httpURLConnectionMock).setRequestMethod("GET");
        verify(httpURLConnectionMock).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertArrayEquals(response, result.body());
        verify(smallerSizeByteArray).close();
    }

    @Test
    public void chunkRead_shouldHandleInputStreamLargerThanDefaultChunkSize() throws Exception {
        byte[] response = createByteArrayOfSize(40);
        InputStream largerSizeByteArray = spy(new ByteArrayInputStream(response));

        when(httpURLConnectionMock.getInputStream()).thenReturn(largerSizeByteArray);
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);

        Response result = testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);

        verify(httpURLConnectionMock).setRequestMethod("GET");
        verify(httpURLConnectionMock).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertArrayEquals(response, result.body());
    }

    @Test
    public void doRequest_shouldFailForNonOkStatusCode() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);

        try {
            testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);
            fail("ResourceException expected");
        } catch (ResourceException re) {
            assertEquals(HTTP_BAD_REQUEST, re.getResponseCode());
            assertEquals(ERROR_BODY, re.getResponseBody());
            verify(errorStreamSpy).close();
        }
    }

    @Test
    public void doRequest_shouldCloseConnectionAfterParsingError() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamSpy);

        doThrow(new IOException()).when(inputStreamSpy).read(any(byte[].class));

        try {
            testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);
            fail("IOException expected");
        } catch (IOException ioe) {
            verify(inputStreamSpy).close();
        }
    }

    @Test
    public void doRequest_shouldSuppressExceptionFromClosingInputStream() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamSpy);
        doThrow(new IOException()).when(inputStreamSpy).close();

        testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);

        verify(inputStreamSpy).close();
    }

    @Test
    public void doRequest_shouldSuppressExceptionFromClosingErrorStream() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);
        doThrow(new IOException()).when(errorStreamSpy).close();

        try {
            testObj.doRequest(urlConnectorMock, HttpMethod.GET, null, TEST_HEADERS);
            fail("ResourceException expected");
        } catch (ResourceException re) {
            assertEquals(HTTP_BAD_REQUEST, re.getResponseCode());
            assertEquals(ERROR_BODY, re.getResponseBody());
            verify(errorStreamSpy).close();
        }
    }

    @Test
    public void doRequest_shouldSendBodyAndReturnResponse() throws Exception {
        when(httpURLConnectionMock.getResponseCode()).thenReturn(HTTP_OK);
        when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamSpy);

        Response result = testObj.doRequest(urlConnectorMock, HttpMethod.POST, requestBody, TEST_HEADERS);

        verify(httpURLConnectionMock).setRequestMethod("POST");
        verify(httpURLConnectionMock).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        verify(httpURLConnectionMock.getOutputStream()).write(requestBody);
        assertArrayEquals(responseBody, result.body());
    }

    private byte[] createByteArrayOfSize(int size) {
        byte[] output = new byte[size];
        for (int i = 0; i < size; i++) {
            output[i] = (byte) i;
        }
        return output;
    }

}

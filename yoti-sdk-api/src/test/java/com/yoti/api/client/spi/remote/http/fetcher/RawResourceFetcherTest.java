package com.yoti.api.client.spi.remote.http.fetcher;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import com.yoti.api.client.spi.remote.http.HttpMethod;
import com.yoti.api.client.spi.remote.http.ResourceException;
import com.yoti.api.client.spi.remote.http.Response;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class RawResourceFetcherTest {

    private static final int INPUT_ARRAY_SIZE = 40;
    private static final String ERROR_BODY = "errorBody";
    private static final String TEST_HEADER_KEY = "testHeader";
    private static final String TEST_HEADER_VALUE = "testValue";
    private static final Map<String, String> TEST_HEADERS = new HashMap<>();

    @Spy
    @InjectMocks
    RawResourceFetcher resourceFetcher;

    @Mock(answer = RETURNS_DEEP_STUBS) HttpURLConnection connection;
    @Mock UrlConnector urlConnector;

    InputStream inputStreamSpy;
    InputStream errorStreamSpy;

    byte[] requestBody = new byte[] { 5, 6, 7, 8 };
    byte[] responseBody = new byte[] { 1, 2, 3, 4 };

    @BeforeClass
    public static void classSetup() {
        TEST_HEADERS.put(TEST_HEADER_KEY, TEST_HEADER_VALUE);
    }

    @Before
    public void setUp() throws Exception {
        inputStreamSpy = spy(new ByteArrayInputStream(responseBody));
        errorStreamSpy = spy(new ByteArrayInputStream(ERROR_BODY.getBytes()));
        when(urlConnector.getHttpUrlConnection()).thenReturn(connection);
        when(connection.getErrorStream()).thenReturn(errorStreamSpy);
    }

    @Test
    public void doRequest_shouldReturnByteArray() throws Exception {
        when(connection.getInputStream()).thenReturn(inputStreamSpy);
        when(connection.getResponseCode()).thenReturn(HTTP_OK);

        Response result = resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);

        verify(connection).setRequestMethod(HttpMethod.GET.name());
        verify(connection).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertArrayEquals(responseBody, result.body());
        verify(inputStreamSpy).close();
    }

    @Test
    public void chunkRead_shouldHandleInputStreamSmallerThanDefaultChunkSize() throws Exception {
        byte[] response = createByteArrayOfSize(INPUT_ARRAY_SIZE);
        InputStream smallerSizeByteArray = spy(new ByteArrayInputStream(response));

        when(connection.getInputStream()).thenReturn(smallerSizeByteArray);
        when(connection.getResponseCode()).thenReturn(HTTP_OK);

        Response result = resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);

        verify(connection).setRequestMethod(HttpMethod.GET.name());
        verify(connection).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertArrayEquals(response, result.body());
        verify(smallerSizeByteArray).close();
    }

    @Test
    public void chunkRead_shouldHandleInputStreamLargerThanDefaultChunkSize() throws Exception {
        byte[] response = createByteArrayOfSize(INPUT_ARRAY_SIZE);
        InputStream largerSizeByteArray = spy(new ByteArrayInputStream(response));

        when(connection.getInputStream()).thenReturn(largerSizeByteArray);
        when(connection.getResponseCode()).thenReturn(HTTP_OK);

        Response result = resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);

        verify(connection).setRequestMethod(HttpMethod.GET.name());
        verify(connection).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        assertArrayEquals(response, result.body());
    }

    private static byte[] createByteArrayOfSize(int size) {
        byte[] output = new byte[size];
        IntStream.range(0, size).forEach(i -> output[i] = (byte) i);
        return output;
    }

    @Test
    public void doRequest_shouldFailForNonOkStatusCode() throws Exception {
        when(connection.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);

        try {
            resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);
            fail("ResourceException expected");
        } catch (ResourceException ex) {
            assertEquals(HTTP_BAD_REQUEST, ex.code());
            assertEquals(ERROR_BODY, ex.body());
            verify(errorStreamSpy).close();
        }
    }

    @Test
    public void doRequest_shouldCloseConnectionAfterParsingError() throws Exception {
        when(connection.getResponseCode()).thenReturn(HTTP_OK);
        when(connection.getInputStream()).thenReturn(inputStreamSpy);

        doThrow(new IOException()).when(inputStreamSpy).read(any(byte[].class));

        try {
            resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);
            fail("IOException expected");
        } catch (IOException ex) {
            verify(inputStreamSpy).close();
        }
    }

    @Test
    public void doRequest_shouldSuppressExceptionFromClosingInputStream() throws Exception {
        when(connection.getResponseCode()).thenReturn(HTTP_OK);
        when(connection.getInputStream()).thenReturn(inputStreamSpy);
        doThrow(new IOException()).when(inputStreamSpy).close();

        resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);

        verify(inputStreamSpy).close();
    }

    @Test
    public void doRequest_shouldSuppressExceptionFromClosingErrorStream() throws Exception {
        when(connection.getResponseCode()).thenReturn(HTTP_BAD_REQUEST);
        doThrow(new IOException()).when(errorStreamSpy).close();

        try {
            resourceFetcher.doRequest(urlConnector, HttpMethod.GET, null, TEST_HEADERS);
            fail("ResourceException expected");
        } catch (ResourceException ex) {
            assertEquals(HTTP_BAD_REQUEST, ex.code());
            assertEquals(ERROR_BODY, ex.body());
            verify(errorStreamSpy).close();
        }
    }

    @Test
    public void doRequest_shouldSendBodyAndReturnResponse() throws Exception {
        when(connection.getResponseCode()).thenReturn(HTTP_OK);
        when(connection.getInputStream()).thenReturn(inputStreamSpy);

        Response result = resourceFetcher.doRequest(urlConnector, HttpMethod.POST, requestBody, TEST_HEADERS);

        verify(connection).setRequestMethod(HttpMethod.POST.name());
        verify(connection).setRequestProperty(TEST_HEADER_KEY, TEST_HEADER_VALUE);
        verify(connection.getOutputStream()).write(requestBody);
        assertArrayEquals(responseBody, result.body());
    }

}

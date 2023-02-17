package com.yoti.api.client.spi.remote.http.fetcher;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import java.io.IOException;

import com.yoti.api.client.spi.remote.http.Request;
import com.yoti.api.client.spi.remote.http.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonResourceFetcherTest {

    @InjectMocks JsonResourceFetcher resourceFetcher;

    @Mock ObjectMapper objectMapper;
    @Mock RawResourceFetcher rawResourceFetcher;

    @Mock Request request;
    @Mock Response response;

    Object parsedResponse = new Object();

    @Before
    public void setUp() throws Exception {
        when(rawResourceFetcher.doRequest(request)).thenReturn(response);
    }

    @Test
    public void fetchResource_shouldReturnResource() throws Exception {
        when(objectMapper.readValue(response.body(), Object.class)).thenReturn(parsedResponse);

        Object result = resourceFetcher.doRequest(request, Object.class);

        assertSame(parsedResponse, result);
    }

    @Test(expected = IOException.class)
    public void fetchResource_shouldThrowIOExceptionForInvalidResponse() throws Exception {
        when(objectMapper.readValue(response.body(), Object.class)).thenThrow(new IOException());

        resourceFetcher.doRequest(request, Object.class);
    }

    @Test
    public void doRequest_shouldReturnResource() throws Exception {
        when(objectMapper.readValue(response.body(), Object.class)).thenReturn(parsedResponse);

        Object result = resourceFetcher.doRequest(request, Object.class);

        assertSame(parsedResponse, result);
    }

    @Test(expected = IOException.class)
    public void doRequest_shouldThrowIOExceptionForInvalidResponse() throws Exception {
        when(objectMapper.readValue(response.body(), Object.class)).thenThrow(new IOException());

        resourceFetcher.doRequest(request, Object.class);
    }

}

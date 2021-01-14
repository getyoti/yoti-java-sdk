package com.yoti.api.client.spi.remote.call;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonResourceFetcherTest {

    @InjectMocks JsonResourceFetcher testObj;

    @Mock ObjectMapper objectMapperMock;
    @Mock RawResourceFetcher rawResourceFetcherMock;

    @Mock SignedRequest signedRequestMock;
    @Mock SignedRequestResponse signedRequestResponseMock;

    Object parsedResponse = new Object();

    @Before
    public void setUp() throws Exception {
        when(rawResourceFetcherMock.doRequest(signedRequestMock)).thenReturn(signedRequestResponseMock);
    }

    @Test
    public void fetchResource_shouldReturnResource() throws Exception {
        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenReturn(parsedResponse);

        Object result = testObj.doRequest(signedRequestMock, Object.class);

        assertSame(parsedResponse, result);
    }

    @Test(expected = IOException.class)
    public void fetchResource_shouldThrowIOExceptionForInvalidResponse() throws Exception {
        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenThrow(new IOException());

        testObj.doRequest(signedRequestMock, Object.class);
    }

    @Test
    public void doRequest_shouldReturnResource() throws Exception {
        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenReturn(parsedResponse);

        Object result = testObj.doRequest(signedRequestMock, Object.class);

        assertSame(parsedResponse, result);
    }

    @Test(expected = IOException.class)
    public void doRequest_shouldThrowIOExceptionForInvalidResponse() throws Exception {
        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenThrow(new IOException());

        testObj.doRequest(signedRequestMock, Object.class);
    }

}

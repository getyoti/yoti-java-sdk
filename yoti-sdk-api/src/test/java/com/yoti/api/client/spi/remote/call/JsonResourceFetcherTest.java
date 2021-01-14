package com.yoti.api.client.spi.remote.call;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonResourceFetcherTest {

//    private static final String TEST_HEADER_KEY = "testHeader";
//    private static final String TEST_HEADER_VALUE = "testValue";
//    private static final Map<String, String> TEST_HEADERS = new HashMap();
//
//    @InjectMocks JsonResourceFetcher testObj;
//
//    @Mock ObjectMapper objectMapperMock;
//    @Mock RawResourceFetcher rawResourceFetcherMock;
//
//    @Mock UrlConnector urlConnectorMock;
//    @Mock SignedRequestResponse signedRequestResponseMock;
//
//    Object parsedResponse = new Object();
//
//    @BeforeClass
//    public static void classSetup() {
//        TEST_HEADERS.put(TEST_HEADER_KEY, TEST_HEADER_VALUE);
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        when(rawResourceFetcherMock.doRequest(urlConnectorMock, HttpMethod.HTTP_GET, null, TEST_HEADERS)).thenReturn(signedRequestResponseMock);
//    }
//
//    @Test
//    public void fetchResource_shouldReturnResource() throws Exception {
//        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenReturn(parsedResponse);
//
//        Object result = testObj.fetchResource(urlConnectorMock, TEST_HEADERS, Object.class);
//
//        assertSame(parsedResponse, result);
//    }
//
//    @Test(expected = IOException.class)
//    public void fetchResource_shouldThrowIOExceptionForInvalidResponse() throws Exception {
//        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenThrow(new IOException());
//
//        testObj.fetchResource(urlConnectorMock, TEST_HEADERS, Object.class);
//    }
//
//    @Test
//    public void doRequest_shouldReturnResource() throws Exception {
//        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenReturn(parsedResponse);
//
//        Object result = testObj.doRequest(urlConnectorMock, HttpMethod.HTTP_GET, null, TEST_HEADERS, Object.class);
//
//        assertSame(parsedResponse, result);
//    }
//
//    @Test(expected = IOException.class)
//    public void doRequest_shouldThrowIOExceptionForInvalidResponse() throws Exception {
//        when(objectMapperMock.readValue(signedRequestResponseMock.getResponseBody(), Object.class)).thenThrow(new IOException());
//
//        testObj.doRequest(urlConnectorMock, HttpMethod.HTTP_GET, null, TEST_HEADERS, Object.class);
//    }

}

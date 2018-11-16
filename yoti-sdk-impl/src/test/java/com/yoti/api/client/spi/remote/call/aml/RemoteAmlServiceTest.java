package com.yoti.api.client.spi.remote.call.aml;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.AmlException;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoteAmlServiceTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String GENERATED_PATH = "generatedPath";
    private static final String SERIALIZED_BODY = "serializedBody";
    private static final byte[] BODY_BYTES = SERIALIZED_BODY.getBytes();
    private static final String SOME_SIGNATURE = "someSignature";
    private static final Map<String, String> SOME_HEADERS = new HashMap<>();

    @InjectMocks RemoteAmlService testObj;

    @Mock PathFactory pathFactoryMock;
    @Mock HeadersFactory headersFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock ResourceFetcher resourceFetcherMock;
    @Mock SignedMessageFactory signedMessageFactoryMock;

    @Mock AmlProfile amlProfileMock;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;
    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;
    @Mock SimpleAmlResult simpleAmlResultMock;

    @BeforeClass
    public static void setUpClass() {
        SOME_HEADERS.put("someKey", "someValue");
    }

    @Before
    public void setUp() {
        when(pathFactoryMock.createAmlPath(SOME_APP_ID)).thenReturn(GENERATED_PATH);
        when(headersFactoryMock.create(SOME_SIGNATURE)).thenReturn(SOME_HEADERS);
    }

    @Test
    public void shouldPerformAmlCheck() throws Exception {
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, GENERATED_PATH, BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(BODY_BYTES), eq(SOME_HEADERS), eq(SimpleAmlResult.class)))
                .thenReturn(simpleAmlResultMock);

        SimpleAmlResult result = testObj.performCheck(keyPairMock, SOME_APP_ID, amlProfileMock);

        verify(resourceFetcherMock).postResource(urlConnectorCaptor.capture(), eq(BODY_BYTES), eq(SOME_HEADERS), eq(SimpleAmlResult.class));
        assertEquals(YOTI_API_PATH_PREFIX + GENERATED_PATH, getPath(urlConnectorCaptor.getValue()));
        assertSame(result, simpleAmlResultMock);
    }

    private String getPath(UrlConnector urlConnector) throws Exception {
        return new URL(urlConnector.getUrlString()).getPath();
    }

    @Test
    public void shouldWrapIOException() throws Exception {
        IOException ioException = new IOException();
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, GENERATED_PATH, BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(BODY_BYTES), eq(SOME_HEADERS), eq(SimpleAmlResult.class))).thenThrow(ioException);

        try {
            testObj.performCheck(keyPairMock, SOME_APP_ID, amlProfileMock);
            fail("Expected AmlException");
        } catch (AmlException e) {
            assertSame(ioException, e.getCause());
        }
    }

    @Test
    public void shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(HTTP_UNAUTHORIZED, "Unauthorized", "failed verification");
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, GENERATED_PATH, BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(BODY_BYTES), eq(SOME_HEADERS), eq(SimpleAmlResult.class)))
                .thenThrow(resourceException);

        try {
            testObj.performCheck(keyPairMock, SOME_APP_ID, amlProfileMock);
            fail("Expected AmlException");
        } catch (AmlException e) {
            assertSame(resourceException, e.getCause());
        }
    }

    @Test
    public void shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException generalSecurityException = new GeneralSecurityException();
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, GENERATED_PATH, BODY_BYTES)).thenThrow(generalSecurityException);

        try {
            testObj.performCheck(keyPairMock, SOME_APP_ID, amlProfileMock);
            fail("Expected AmlException");
        } catch (AmlException e) {
            assertSame(generalSecurityException, e.getCause());
        }
    }

    @Test
    public void shouldWrapJsonProcessingException() throws Exception {
        JsonProcessingException jsonProcessingException = new JsonProcessingException("") {};
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenThrow(jsonProcessingException);

        try {
            testObj.performCheck(keyPairMock, SOME_APP_ID, amlProfileMock);
            fail("Expected AmlException");
        } catch (AmlException e) {
            assertSame(jsonProcessingException, e.getCause());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws Exception {
        testObj.performCheck(null, SOME_APP_ID, amlProfileMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws Exception {
        testObj.performCheck(keyPairMock, null, amlProfileMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAmlProfile() throws Exception {
        testObj.performCheck(keyPairMock, SOME_APP_ID, null);
    }

}

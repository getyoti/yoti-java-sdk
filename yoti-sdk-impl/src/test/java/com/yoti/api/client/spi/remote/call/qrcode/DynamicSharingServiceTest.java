package com.yoti.api.client.spi.remote.call.qrcode;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.qrcode.DynamicScenario;
import com.yoti.api.client.qrcode.DynamicShareException;
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
public class DynamicSharingServiceTest {

    private static final String APP_ID = "appId";
    private static final String DYNAMIC_QRCODE_PATH = "dynamicQRCodePath";
    private static final String SOME_BODY = "someBody";
    private static final byte[] SOME_BODY_BYTES = SOME_BODY.getBytes();
    private static final String SOME_SIGNATURE = "someSignature";
    private static final Map<String, String> SOME_HEADERS = new HashMap<>();

    @InjectMocks DynamicSharingService testObj;

    @Mock PathFactory pathFactoryMock;
    @Mock HeadersFactory headersFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock ResourceFetcher resourceFetcherMock;
    @Mock SignedMessageFactory signedMessageFactoryMock;

    @Mock DynamicScenario simpleDynamicScenarioMock;
    @Mock SimpleShareUrlResult simpleShareUrlResultMock;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;

    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;

    @BeforeClass
    public static void setUpClass() {
        SOME_HEADERS.put("someKey", "someValue");
    }

    @Before
    public void setUp() {
        when(pathFactoryMock.createDynamicSharingPath(APP_ID)).thenReturn(DYNAMIC_QRCODE_PATH);
        when(headersFactoryMock.create(SOME_SIGNATURE)).thenReturn(SOME_HEADERS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws Exception {
        testObj.createShareUrl(null, keyPairMock, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws Exception {
        testObj.createShareUrl(APP_ID, null, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullDynamicScenario() throws Exception {
        testObj.createShareUrl(APP_ID, keyPairMock, null);
    }

    @Test
    public void shouldThrowDynamicShareExceptionWhenParsingFails() throws Exception {
        JsonProcessingException jsonProcessingException = new JsonProcessingException("") {};
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenThrow(jsonProcessingException);

        try {
            testObj.createShareUrl(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(jsonProcessingException, ex.getCause());
        }
    }

    @Test
    public void shouldWrapSecurityExceptionInDynamicShareException() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        GeneralSecurityException securityException = new GeneralSecurityException();
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES)).thenThrow(securityException);

        try {
            testObj.createShareUrl(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(securityException, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionForIOError() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        IOException ioException = new IOException();
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY_BYTES), eq(SOME_HEADERS), eq(SimpleShareUrlResult.class))).thenThrow(ioException);

        try {
            testObj.createShareUrl(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(ioException, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        ResourceException resourceEx = new ResourceException(HTTP_NOT_FOUND, "Test exception");
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY_BYTES), eq(SOME_HEADERS), eq(SimpleShareUrlResult.class))).thenThrow(resourceEx);

        try {
            testObj.createShareUrl(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a DynamicShareException");
        } catch (DynamicShareException ex) {
            assertSame(resourceEx, ex.getCause());
        }
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY_BYTES), eq(SOME_HEADERS), eq(SimpleShareUrlResult.class)))
                .thenReturn(simpleShareUrlResultMock);

        SimpleShareUrlResult result = testObj.createShareUrl(APP_ID, keyPairMock, simpleDynamicScenarioMock);

        verify(resourceFetcherMock).postResource(urlConnectorCaptor.capture(), eq(SOME_BODY_BYTES), eq(SOME_HEADERS), eq(SimpleShareUrlResult.class));
        assertEquals(YOTI_API_PATH_PREFIX + DYNAMIC_QRCODE_PATH, getPath(urlConnectorCaptor.getValue()));
        assertSame(simpleShareUrlResultMock, result);
    }

    private String getPath(UrlConnector urlConnector) throws Exception {
        return new URL(urlConnector.getUrlString()).getPath();
    }

}

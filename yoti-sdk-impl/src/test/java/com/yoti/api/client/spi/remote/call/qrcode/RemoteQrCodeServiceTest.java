package com.yoti.api.client.spi.remote.call.qrcode;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

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
import java.util.Map;

import com.yoti.api.client.qrcode.DynamicScenario;
import com.yoti.api.client.qrcode.QRCodeException;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoteQrCodeServiceTest {

    private static final String APP_ID = "appId";
    private static final String DYNAMIC_QRCODE_PATH = "dynamicQRCodePath";
    private static final String SOME_BODY = "someBody";
    private static final byte[] SOME_BODY_BYTES = SOME_BODY.getBytes();
    private static final String SOME_SIGNATURE = "someSignature";

    @InjectMocks RemoteQrCodeService testObj;

    @Mock PathFactory pathFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock ResourceFetcher resourceFetcherMock;
    @Mock SignedMessageFactory signedMessageFactoryMock;

    @Mock DynamicScenario simpleDynamicScenarioMock;
    @Mock SimpleQrCode simpleQrCodeMock;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;

    @Captor ArgumentCaptor<Map<String, String>> headersCaptor;
    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;

    @Before
    public void setUp() {
        when(pathFactoryMock.createQrCodePath(APP_ID)).thenReturn(DYNAMIC_QRCODE_PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws Exception {
        testObj.requestQRCode(null, keyPairMock, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws Exception {
        testObj.requestQRCode(APP_ID, null, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullDynamicScenario() throws Exception {
        testObj.requestQRCode(APP_ID, keyPairMock, null);
    }

    @Test
    public void shouldThrowQRCodeExceptionWhenParsingFails() throws JsonProcessingException {
        JsonProcessingException jsonProcessingException = new JsonProcessingException("") {};
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenThrow(jsonProcessingException);

        try {
            testObj.requestQRCode(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(jsonProcessingException, ex.getCause());
        }
    }

    @Test
    public void shouldWrapSecurityExceptionInQRCodeException() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        GeneralSecurityException securityException = new GeneralSecurityException();
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES)).thenThrow(securityException);

        try {
            testObj.requestQRCode(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(securityException, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionForIOError() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        IOException ioException = new IOException();
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY_BYTES), any(Map.class), eq(SimpleQrCode.class))).thenThrow(ioException);

        try {
            testObj.requestQRCode(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(ioException, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        ResourceException resourceEx = new ResourceException(HTTP_NOT_FOUND, "Test exception");
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY_BYTES), any(Map.class), eq(SimpleQrCode.class))).thenThrow(resourceEx);

        try {
            testObj.requestQRCode(APP_ID, keyPairMock, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(resourceEx, ex.getCause());
        }
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        when(signedMessageFactoryMock.create(keyPairMock.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY_BYTES)).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY_BYTES), any(Map.class), eq(SimpleQrCode.class))).thenReturn(
                simpleQrCodeMock);

        SimpleQrCode result = testObj.requestQRCode(APP_ID, keyPairMock, simpleDynamicScenarioMock);

        verify(resourceFetcherMock).postResource(urlConnectorCaptor.capture(), eq(SOME_BODY_BYTES), headersCaptor.capture(), eq(SimpleQrCode.class));
        assertEquals(YOTI_API_PATH_PREFIX + DYNAMIC_QRCODE_PATH, getPath(urlConnectorCaptor.getValue()));
        assertEquals(SOME_SIGNATURE, headersCaptor.getValue().get(DIGEST_HEADER));
        assertEquals(JAVA, headersCaptor.getValue().get(YOTI_SDK_HEADER));
        assertEquals(SDK_VERSION, headersCaptor.getValue().get(YOTI_SDK_VERSION_HEADER));
        assertSame(simpleQrCodeMock, result);
    }

    private String getPath(UrlConnector urlConnector) throws Exception {
        return new URL(urlConnector.getUrlString()).getPath();
    }

}

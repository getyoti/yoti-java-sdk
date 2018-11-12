package com.yoti.api.client.spi.remote.call.qrcode;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Map;
import com.yoti.api.client.qrcode.QRCodeException;
import com.yoti.api.client.qrcode.SimpleDynamicScenario;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoteQrCodeServiceTest {

    private static final String APP_ID = "appId";
    private static final String DYNAMIC_QRCODE_PATH = "dynamicQRCodePath";
    private static final String SOME_BODY = "someBody";
    private static final String SOME_SIGNATURE = "someSignature";

    @InjectMocks
    private RemoteQrCodeService service;

    @Mock
    private PathFactory pathFactoryMock;
    @Mock
    private ObjectMapper objectMapperMock;
    @Mock
    private ResourceFetcher resourceFetcherMock;
    @Mock
    private SignedMessageFactory signedMessageFactoryMock;

    @Mock
    private SimpleDynamicScenario simpleDynamicScenarioMock;
    @Mock
    private SimpleQrCode simpleQrCodeMock;

    @Captor
    private ArgumentCaptor<Map<String, String>> headersCaptor;
    @Captor
    private ArgumentCaptor<UrlConnector> urlConnectorCaptor;

    private KeyPair keyPair;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(KEY_PAIR_PEM);
        when(pathFactoryMock.createQrCodePath(APP_ID)).thenReturn(DYNAMIC_QRCODE_PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAppId() throws QRCodeException {
        service.requestQRCode(null, keyPair, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullKeyPair() throws QRCodeException {
        service.requestQRCode(APP_ID, null, simpleDynamicScenarioMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullDynamicScenario() throws QRCodeException {
        service.requestQRCode(APP_ID, keyPair, null);
    }

    @Test
    public void shouldThrowQRCodeExceptionWhenParsingFails() throws JsonProcessingException {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenThrow(new JsonProcessingException("") {});

        try {
            service.requestQRCode(APP_ID, keyPair, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertTrue(ex.getCause() instanceof JsonProcessingException);
        }
    }

    @Test
    public void shouldWrapSecurityExceptionInQRCodeException() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);

        GeneralSecurityException generalSecurityEx = new GeneralSecurityException();
        when(signedMessageFactoryMock.create(keyPair.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY.getBytes()))
                .thenThrow(generalSecurityEx);

        try {
            service.requestQRCode(APP_ID, keyPair, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(generalSecurityEx, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionForIOError() throws IOException, ResourceException {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);

        IOException ioEx = new IOException("Test Exception");
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY.getBytes()), any(Map.class), eq(SimpleQrCode.class)))
                .thenThrow(ioEx);

        try {
            service.requestQRCode(APP_ID, keyPair, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(ioEx, ex.getCause());
        }
    }

    @Test
    public void shouldThrowExceptionWithResourceExceptionCause() throws IOException, ResourceException {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);

        ResourceException resourceEx = new ResourceException(HTTP_NOT_FOUND, "Test exception");
        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY.getBytes()), any(Map.class), eq(SimpleQrCode.class)))
                .thenThrow(resourceEx);

        try {
            service.requestQRCode(APP_ID, keyPair, simpleDynamicScenarioMock);
            fail("Expected a QRCodeException");
        } catch (QRCodeException ex) {
            assertSame(resourceEx, ex.getCause());
        }
    }

    @Test
    public void shouldReturnReceiptForCorrectRequest() throws Exception {
        when(objectMapperMock.writeValueAsString(simpleDynamicScenarioMock)).thenReturn(SOME_BODY);
        when(signedMessageFactoryMock.create(keyPair.getPrivate(), HTTP_POST, DYNAMIC_QRCODE_PATH, SOME_BODY.getBytes()))
                .thenReturn(SOME_SIGNATURE);

        when(resourceFetcherMock.postResource(any(UrlConnector.class), eq(SOME_BODY.getBytes()), any(Map.class), eq(SimpleQrCode.class)))
                .thenReturn(simpleQrCodeMock);

        SimpleQrCode response = service.requestQRCode(APP_ID, keyPair, simpleDynamicScenarioMock);

        verify(resourceFetcherMock).postResource(urlConnectorCaptor.capture(), eq(SOME_BODY.getBytes()), headersCaptor.capture(), eq(SimpleQrCode.class));

        assertEquals(YOTI_API_PATH_PREFIX + DYNAMIC_QRCODE_PATH, new URL(urlConnectorCaptor.getValue().getUrlString()).getPath());

        assertEquals(SOME_SIGNATURE, headersCaptor.getValue().get(DIGEST_HEADER));
        assertEquals(JAVA, headersCaptor.getValue().get(YOTI_SDK_HEADER));

        assertSame(simpleQrCodeMock, response);
    }

}

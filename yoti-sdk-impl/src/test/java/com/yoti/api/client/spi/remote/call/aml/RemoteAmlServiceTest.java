package com.yoti.api.client.spi.remote.call.aml;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_API_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.YotiConstants;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignatureFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class RemoteAmlServiceTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String GENERATED_PATH = "generatedPath";
    private static final String SERIALIZED_BODY = "serializedBody";
    private static final String SOME_SIGNATURE = "someSignature";

    @InjectMocks RemoteAmlService testObj;

    @Mock PathFactory pathFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock ResourceFetcher resourceFetcherMock;
    @Mock SignatureFactory signatureFactoryMock;

    @Mock AmlProfile amlProfileMock;
    KeyPair keyPair;
    @Captor ArgumentCaptor<Map<String, String>> headersCaptor;
    @Captor ArgumentCaptor<UrlConnector> urlConnectorCaptor;
    @Mock SimpleAmlResult simpleAmlResultMock;

    @Before
    public void setUp() throws Exception {
        keyPair = generateKeyPairFrom(KEY_PAIR_PEM);

        when(pathFactoryMock.createAmlPath(SOME_APP_ID)).thenReturn(GENERATED_PATH);
    }

    @Test
    public void shouldPerformAmlCheck() throws Exception {
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signatureFactoryMock.create(keyPair.getPrivate(), HTTP_POST, GENERATED_PATH, SERIALIZED_BODY.getBytes())).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), any(byte[].class), any(Map.class), eq(SimpleAmlResult.class)))
                .thenReturn(simpleAmlResultMock);

        SimpleAmlResult result = testObj.performCheck(keyPair, SOME_APP_ID, amlProfileMock);

        verify(resourceFetcherMock).postResource(urlConnectorCaptor.capture(), eq(SERIALIZED_BODY.getBytes()), headersCaptor.capture(), eq(SimpleAmlResult.class));
        assertUrl(urlConnectorCaptor.getValue());
        assertHeaders(headersCaptor.getValue());
        assertSame(result, simpleAmlResultMock);
    }

    private void assertUrl(UrlConnector urlConnector) throws MalformedURLException {
        URL url = new URL(urlConnector.getUrlString());
        assertEquals(YOTI_API_PATH_PREFIX + GENERATED_PATH, url.getPath());
    }

    private void assertHeaders(Map<String, String> headers) throws Exception {
        assertDigest(headers.get(DIGEST_HEADER));
        assertYotiSDK(headers.get(YOTI_SDK_HEADER));
    }

    private void assertDigest(String digestValue) throws Exception {
        assertEquals(SOME_SIGNATURE, digestValue);
    }

    private void assertYotiSDK(String sdkValue) {
        assertEquals(YotiConstants.JAVA, sdkValue);
    }

    @Test
    public void shouldWrapIOException() throws Exception {
        IOException ioException = new IOException();
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signatureFactoryMock.create(keyPair.getPrivate(), HTTP_POST, GENERATED_PATH, SERIALIZED_BODY.getBytes())).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), any(byte[].class), any(Map.class), eq(SimpleAmlResult.class))).thenThrow(ioException);

        try {
            testObj.performCheck(keyPair, SOME_APP_ID, amlProfileMock);
            fail("Expected AmlException");
        } catch (AmlException e) {
            assertSame(ioException, e.getCause());
        }
    }

    @Test
    public void shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(HTTP_UNAUTHORIZED, "failed verification");
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signatureFactoryMock.create(keyPair.getPrivate(), HTTP_POST, GENERATED_PATH, SERIALIZED_BODY.getBytes())).thenReturn(SOME_SIGNATURE);
        when(resourceFetcherMock.postResource(any(UrlConnector.class), any(byte[].class), any(Map.class), eq(SimpleAmlResult.class)))
                .thenThrow(resourceException);

        try {
            testObj.performCheck(keyPair, SOME_APP_ID, amlProfileMock);
            fail("Expected AmlException");
        } catch (AmlException e) {
            assertSame(resourceException, e.getCause());
        }
    }

    @Test
    public void shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException generalSecurityException = new GeneralSecurityException();
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signatureFactoryMock.create(keyPair.getPrivate(), HTTP_POST, GENERATED_PATH, SERIALIZED_BODY.getBytes())).thenThrow(generalSecurityException);

        try {
            testObj.performCheck(keyPair, SOME_APP_ID, amlProfileMock);
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
            testObj.performCheck(keyPair, SOME_APP_ID, amlProfileMock);
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
        testObj.performCheck(keyPair, null, amlProfileMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithNullAmlProfile() throws Exception {
        testObj.performCheck(keyPair, SOME_APP_ID, null);
    }

}

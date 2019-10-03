package com.yoti.api.client.spi.remote.call.aml;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.AmlException;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoteAmlServiceTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String GENERATED_PATH = "generatedPath";
    private static final String SERIALIZED_BODY = "serializedBody";
    private static final byte[] BODY_BYTES = SERIALIZED_BODY.getBytes();
    private static final Map<String, String> SOME_HEADERS = new HashMap<>();

    @InjectMocks RemoteAmlService testObj;

    @Mock UnsignedPathFactory unsignedPathFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock(answer = RETURNS_SELF) SignedRequestBuilder signedRequestBuilderMock;

    @Mock AmlProfile amlProfileMock;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;
    @Mock SignedRequest signedRequestMock;
    @Mock SimpleAmlResult simpleAmlResultMock;

    @BeforeClass
    public static void setUpClass() {
        SOME_HEADERS.put("someKey", "someValue");
    }

    @Before
    public void setUp() {
        when(unsignedPathFactoryMock.createAmlPath(SOME_APP_ID)).thenReturn(GENERATED_PATH);
    }

    @Test
    public void shouldPerformAmlCheck() throws Exception {
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleAmlResult.class)).thenReturn(simpleAmlResultMock);

        SimpleAmlResult result = testObj.performCheck(keyPairMock, SOME_APP_ID, amlProfileMock);

        verify(signedRequestBuilderMock).withKeyPair(keyPairMock);
        verify(signedRequestBuilderMock).withBaseUrl(DEFAULT_YOTI_API_URL);
        verify(signedRequestBuilderMock).withEndpoint(GENERATED_PATH);
        verify(signedRequestBuilderMock).withPayload(BODY_BYTES);
        verify(signedRequestBuilderMock).withHttpMethod(HTTP_POST);
        assertSame(result, simpleAmlResultMock);
    }

    @Test
    public void shouldWrapIOException() throws Exception {
        IOException ioException = new IOException();
        when(objectMapperMock.writeValueAsString(amlProfileMock)).thenReturn(SERIALIZED_BODY);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleAmlResult.class)).thenThrow(ioException);

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
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleAmlResult.class)).thenThrow(resourceException);

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
        when(signedRequestBuilderMock.build()).thenThrow(generalSecurityException);

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

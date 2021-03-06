package com.yoti.api.client.sandbox;

import static com.yoti.api.client.sandbox.YotiSandboxClient.YOTI_SANDBOX_PATH_PREFIX;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Map;

import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.sandbox.profile.request.YotiTokenRequest;
import com.yoti.api.client.sandbox.profile.response.YotiTokenResponse;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class YotiSandboxClientTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String SOME_PATH = "somePath";
    private static final byte[] BODY_BYTES = {};
    private static final String SOME_TOKEN = "someToken";

    YotiSandboxClient testObj;

    @Mock SandboxPathFactory sandboxPathFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock ResourceFetcher resourceFetcherMock;

    @Mock KeyPairSource keyPairSourceMock;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPairMock;

    @Mock SignedRequestBuilderFactory signedRequestBuilderFactoryMock;
    @Mock(answer = RETURNS_SELF) SignedRequestBuilder signedRequestBuilderMock;
    @Mock SignedRequest signedRequestMock;

    @Mock YotiTokenRequest yotiTokenRequestMock;
    @Mock YotiTokenResponse yotiTokenResponseMock;

    @Before
    public void setUp() throws Exception {
        when(signedRequestBuilderFactoryMock.create()).thenReturn(signedRequestBuilderMock);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        testObj = new YotiSandboxClient(SOME_APP_ID, keyPairMock, sandboxPathFactoryMock, objectMapperMock, resourceFetcherMock, signedRequestBuilderFactoryMock);
    }

    @Test
    public void builder_shouldThrowForMissingAppId() {
        try {
            YotiSandboxClient.builder().build();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("appId"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldThrowForMissingKey() {
        try {
            YotiSandboxClient.builder()
                    .forApplication(SOME_APP_ID)
                    .build();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("keyPair"));
            return;
        }
        fail("Expected an Exception");
    }

    @Test
    public void builder_shouldCreateClient() throws Exception {
        when(keyPairSourceMock.getFromStream(any(KeyStreamVisitor.class))).thenReturn(keyPairMock);

        YotiSandboxClient.builder()
                .forApplication(SOME_APP_ID)
                .withKeyPair(keyPairSourceMock)
                .build();
    }

    @Test
    public void setupSharingProfile_shouldWrapIOException() throws Exception {
        JsonMappingException jsonMappingException = mock(JsonMappingException.class);
        when(objectMapperMock.writeValueAsBytes(yotiTokenRequestMock)).thenThrow(jsonMappingException);

        try {
            testObj.setupSharingProfile(yotiTokenRequestMock);
        } catch (SandboxException e) {
            assertEquals(jsonMappingException, e.getCause());
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void setupSharingProfile_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(401, null, null);
        when(objectMapperMock.writeValueAsBytes(yotiTokenRequestMock)).thenReturn(BODY_BYTES);
        when(resourceFetcherMock.doRequest(signedRequestMock, YotiTokenResponse.class)).thenThrow(resourceException);

        try {
            testObj.setupSharingProfile(yotiTokenRequestMock);
        } catch (SandboxException e) {
            assertEquals(resourceException, e.getCause());
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void setupSharingProfile_shouldReturnTokenFromSandbox() throws Exception {
        when(sandboxPathFactoryMock.createSandboxPath(SOME_APP_ID)).thenReturn(SOME_PATH);
        when(objectMapperMock.writeValueAsBytes(yotiTokenRequestMock)).thenReturn(BODY_BYTES);
        when(resourceFetcherMock.doRequest(signedRequestMock, YotiTokenResponse.class)).thenReturn(yotiTokenResponseMock);
        when(yotiTokenResponseMock.getToken()).thenReturn(SOME_TOKEN);

        String result = testObj.setupSharingProfile(yotiTokenRequestMock);

        verify(resourceFetcherMock).doRequest(signedRequestMock, YotiTokenResponse.class);
        assertEquals(SOME_TOKEN, result);
    }

}

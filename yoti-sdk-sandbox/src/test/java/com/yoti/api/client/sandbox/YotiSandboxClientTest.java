package com.yoti.api.client.sandbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Answers.RETURNS_SELF;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.security.KeyPair;

import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.sandbox.profile.request.YotiTokenRequest;
import com.yoti.api.client.sandbox.profile.response.YotiTokenResponse;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class YotiSandboxClientTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String SOME_PATH = "somePath";
    private static final byte[] BODY_BYTES = {};
    private static final String SOME_TOKEN = "someToken";

    YotiSandboxClient testObj;

    @Mock SandboxPathFactory sandboxPathFactory;
    @Mock ObjectMapper objectMapper;
    @Mock JsonResourceFetcher resourceFetcher;

    @Mock KeyPairSource keyPairSource;
    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPair;

    @Mock SignedRequestBuilderFactory signedRequestBuilderFactory;
    @Mock(answer = RETURNS_SELF) SignedRequestBuilder signedRequestBuilder;
    @Mock SignedRequest signedRequest;

    @Mock YotiTokenRequest yotiTokenRequest;
    @Mock YotiTokenResponse yotiTokenResponse;

    @Before
    public void setUp() throws Exception {
        when(signedRequestBuilderFactory.create()).thenReturn(signedRequestBuilder);
        when(signedRequestBuilder.build()).thenReturn(signedRequest);
        testObj = new YotiSandboxClient(SOME_APP_ID, keyPair,
                sandboxPathFactory, objectMapper, resourceFetcher, signedRequestBuilderFactory
        );
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
        when(keyPairSource.getFromStream(any(KeyStreamVisitor.class))).thenReturn(keyPair);

        YotiSandboxClient.builder()
                .forApplication(SOME_APP_ID)
                .withKeyPair(keyPairSource)
                .build();
    }

    @Test
    public void setupSharingProfile_shouldWrapIOException() throws Exception {
        JsonMappingException jsonMappingException = mock(JsonMappingException.class);
        when(objectMapper.writeValueAsBytes(yotiTokenRequest)).thenThrow(jsonMappingException);

        try {
            testObj.setupSharingProfile(yotiTokenRequest);
        } catch (SandboxException e) {
            assertEquals(jsonMappingException, e.getCause());
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void setupSharingProfile_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(401, null, null);
        when(objectMapper.writeValueAsBytes(yotiTokenRequest)).thenReturn(BODY_BYTES);
        when(resourceFetcher.doRequest(signedRequest, YotiTokenResponse.class)).thenThrow(resourceException);

        try {
            testObj.setupSharingProfile(yotiTokenRequest);
        } catch (SandboxException e) {
            assertEquals(resourceException, e.getCause());
            return;
        }

        fail("Expected an Exception");
    }

    @Test
    public void setupSharingProfile_shouldReturnTokenFromSandbox() throws Exception {
        when(sandboxPathFactory.createSandboxPath(SOME_APP_ID)).thenReturn(SOME_PATH);
        when(objectMapper.writeValueAsBytes(yotiTokenRequest)).thenReturn(BODY_BYTES);
        when(resourceFetcher.doRequest(signedRequest, YotiTokenResponse.class)).thenReturn(yotiTokenResponse);
        when(yotiTokenResponse.getToken()).thenReturn(SOME_TOKEN);

        String result = testObj.setupSharingProfile(yotiTokenRequest);

        verify(resourceFetcher).doRequest(signedRequest, YotiTokenResponse.class);
        assertEquals(SOME_TOKEN, result);
    }

}

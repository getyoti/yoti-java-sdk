package com.yoti.api.client.spi.remote.call.identity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.identity.MatchRequest;
import com.yoti.api.client.identity.MatchResult;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilder;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;
import com.yoti.json.ResourceMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DigitalIdentityServiceTest {

    private static final String SDK_ID = "anSdkId";
    private static final String SESSION_ID = "aSessionId";
    private static final String QR_CODE_ID = "aQrCodeId";
    private static final String SESSION_CREATION_PATH = "aSessionCreationPath";
    private static final String DIGITAL_ID_MATCH_PATH = "aDigitalIdMatchPath";
    private static final String POST = "POST";
    private static final byte[] A_BODY_BYTES = "aBody".getBytes(StandardCharsets.UTF_8);

    @Spy @InjectMocks DigitalIdentityService identityService;

    @Mock UnsignedPathFactory unsignedPathFactory;
    @Mock(answer = RETURNS_DEEP_STUBS) YotiHttpRequestBuilder yotiHttpRequestBuilder;
    @Mock YotiHttpRequestBuilderFactory requestBuilderFactory;

    @Mock(answer = RETURNS_DEEP_STUBS) KeyPair keyPair;
    @Mock YotiHttpRequest yotiHttpRequest;

    @Mock ShareSessionRequest shareSessionRequest;
    @Mock ShareSession shareSession;

    @Mock MatchRequest matchRequest;
    @Mock MatchResult matchResult;

    @Before
    public void setUp() {
        when(unsignedPathFactory.createIdentitySessionPath()).thenReturn(SESSION_CREATION_PATH);
        when(unsignedPathFactory.createIdentityMatchPath()).thenReturn(DIGITAL_ID_MATCH_PATH);
    }

    @Test
    public void createShareSession_NullSdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareSession(null, keyPair, shareSessionRequest)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void createShareSession_EmptySdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareSession("", keyPair, shareSessionRequest)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void createShareSession_NullKeyPair_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareSession(SDK_ID, null, shareSessionRequest)
        );

        assertThat(ex.getMessage(), containsString("Application Key Pair"));
    }

    @Test
    public void createShareSession_NullShareSessionRequest_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareSession(SDK_ID, keyPair, null)
        );

        assertThat(ex.getMessage(), containsString("Share Session request"));
    }

    @Test
    public void createShareSession_SerializingWrongPayload_Exception() {
        JsonProcessingException causeEx = new JsonProcessingException("serialization error") {};

        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(shareSessionRequest)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(SDK_ID, keyPair, shareSessionRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof JsonProcessingException);
            assertThat(cause.getMessage(), containsString("serialization error"));
        }
    }

    @Test
    public void createShareSession_BuildingRequestWithWrongUri_Exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(shareSessionRequest)).thenReturn(A_BODY_BYTES);
            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            String exMessage = "URI wrong format";
            URISyntaxException causeEx = new URISyntaxException("", exMessage);
            when(identityService.createSignedRequest(SDK_ID, keyPair, SESSION_CREATION_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(SDK_ID, keyPair, shareSessionRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof URISyntaxException);
            assertThat(cause.getMessage(), containsString(exMessage));
        }
    }

    @Test
    public void createShareSession_BuildingRequestWithWrongQueryParams_Exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(shareSessionRequest)).thenReturn(A_BODY_BYTES);

            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            String exMessage = "Wrong query params format";
            UnsupportedEncodingException causeEx = new UnsupportedEncodingException(exMessage);
            when(identityService.createSignedRequest(SDK_ID, keyPair, SESSION_CREATION_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(SDK_ID, keyPair, shareSessionRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof UnsupportedEncodingException);
            assertThat(cause.getMessage(), containsString(exMessage));
        }
    }

    @Test
    public void createShareSession_BuildingRequestWithWrongDigest_Exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(shareSessionRequest)).thenReturn(A_BODY_BYTES);

            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            String exMessage = "Wrong digest";
            GeneralSecurityException causeEx = new GeneralSecurityException(exMessage);
            when(identityService.createSignedRequest(SDK_ID, keyPair, SESSION_CREATION_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(SDK_ID, keyPair, shareSessionRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof GeneralSecurityException);
            assertThat(cause.getMessage(), containsString(exMessage));
        }
    }

    @Test
    public void createShareSession_SessionRequest_exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(shareSessionRequest)).thenReturn(A_BODY_BYTES);

            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            when(identityService.createSignedRequest(SDK_ID, keyPair, SESSION_CREATION_PATH, POST, A_BODY_BYTES))
                    .thenReturn(yotiHttpRequest);

            when(yotiHttpRequest.execute(ShareSession.class)).thenReturn(shareSession);

            ShareSession result = identityService.createShareSession(SDK_ID, keyPair, shareSessionRequest);
            assertSame(shareSession, result);
        }
    }

    @Test
    public void createShareQrCode_NullSdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareQrCode(null, keyPair, SESSION_ID)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void createShareQrCode_EmptySdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareQrCode("", keyPair, SESSION_ID)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void createShareQrCode_NullKeyPair_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareQrCode(SDK_ID, null, SESSION_ID)
        );

        assertThat(ex.getMessage(), containsString("Application Key Pair"));
    }

    @Test
    public void createShareQrCode_NullSessionId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareQrCode(SDK_ID, keyPair, null)
        );

        assertThat(ex.getMessage(), containsString("Session ID"));
    }

    @Test
    public void fetchShareQrCode_NullSdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchShareQrCode(null, keyPair, QR_CODE_ID)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void fetchShareQrCode_EmptySdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchShareQrCode("", keyPair, QR_CODE_ID)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void fetchShareQrCode_NullKeyPair_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchShareQrCode(SDK_ID, null, QR_CODE_ID)
        );

        assertThat(ex.getMessage(), containsString("Application Key Pair"));
    }

    @Test
    public void fetchShareQrCode_NullSessionId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchShareQrCode(SDK_ID, keyPair, null)
        );

        assertThat(ex.getMessage(), containsString("QR Code ID"));
    }

    @Test
    public void fetchMatch_NullSdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchMatch(null, keyPair, matchRequest)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void fetchMatch_EmptySdkId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchMatch("", keyPair, matchRequest)
        );

        assertThat(ex.getMessage(), containsString("SDK ID"));
    }

    @Test
    public void fetchMatch_NullKeyPair_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchMatch(SDK_ID, null, matchRequest)
        );

        assertThat(ex.getMessage(), containsString("Application Key Pair"));
    }

    @Test
    public void fetchMatch_NullMatchRequest_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchMatch(SDK_ID, keyPair, null)
        );

        assertThat(ex.getMessage(), notNullValue());
    }

    @Test
    public void fetchMatch_SerializingWrongPayload_Exception() {
        JsonProcessingException causeEx = new JsonProcessingException("serialization error") {};

        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(matchRequest)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(SDK_ID, keyPair, matchRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof JsonProcessingException);
            assertThat(cause.getMessage(), containsString("serialization error"));
        }
    }

    @Test
    public void fetchMatch_BuildingRequestWithWrongEndpoint_Exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(matchRequest)).thenReturn(A_BODY_BYTES);
            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            String exMessage = "URI wrong format";
            URISyntaxException causeEx = new URISyntaxException("", exMessage);
            when(identityService.createSignedRequest(SDK_ID, keyPair, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(SDK_ID, keyPair, matchRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof URISyntaxException);
            assertThat(cause.getMessage(), containsString(exMessage));
        }
    }

    @Test
    public void fetchMatch_BuildingRequestWithWrongQueryParams_Exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(matchRequest)).thenReturn(A_BODY_BYTES);

            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            String exMessage = "Wrong query params format";
            UnsupportedEncodingException causeEx = new UnsupportedEncodingException(exMessage);
            when(identityService.createSignedRequest(SDK_ID, keyPair, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(SDK_ID, keyPair, matchRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof UnsupportedEncodingException);
            assertThat(cause.getMessage(), containsString(exMessage));
        }
    }

    @Test
    public void fetchMatch_BuildingRequestWithWrongDigest_Exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(matchRequest)).thenReturn(A_BODY_BYTES);

            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            String exMessage = "Wrong digest";
            GeneralSecurityException causeEx = new GeneralSecurityException(exMessage);
            when(identityService.createSignedRequest(SDK_ID, keyPair, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(SDK_ID, keyPair, matchRequest)
            );

            Throwable cause = ex.getCause();
            assertTrue(cause instanceof GeneralSecurityException);
            assertThat(cause.getMessage(), containsString(exMessage));
        }
    }

    @Test
    public void fetchMatch_SessionRequest_exception() throws Exception {
        try (MockedStatic<ResourceMapper> mapper = Mockito.mockStatic(ResourceMapper.class)) {
            mapper.when(() -> ResourceMapper.writeValueAsString(matchRequest)).thenReturn(A_BODY_BYTES);

            when(requestBuilderFactory.create()).thenReturn(yotiHttpRequestBuilder);

            when(identityService.createSignedRequest(SDK_ID, keyPair, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenReturn(yotiHttpRequest);

            when(yotiHttpRequest.execute(MatchResult.class)).thenReturn(matchResult);

            MatchResult result = identityService.fetchMatch(SDK_ID, keyPair, matchRequest);
            assertSame(matchResult, result);
        }
    }

}

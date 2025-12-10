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

import com.yoti.api.client.identity.MatchRequest;
import com.yoti.api.client.identity.MatchResult;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilder;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.DigitalIdentitySignedRequestStrategy;
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

    @InjectMocks DigitalIdentityService identityService;

    @Mock UnsignedPathFactory unsignedPathFactoryMock;
    @Mock YotiHttpRequestBuilderFactory requestBuilderFactoryMock;

    @Mock(answer = RETURNS_DEEP_STUBS) YotiHttpRequestBuilder yotiHttpRequestBuilder;

    @Mock DigitalIdentitySignedRequestStrategy authStrategyMock;
    @Mock YotiHttpRequest yotiHttpRequest;
    @Mock ShareSessionRequest shareSessionRequest;
    @Mock ShareSession shareSession;
    @Mock MatchRequest matchRequest;
    @Mock MatchResult matchResult;

    @Before
    public void setUp() {
        when(requestBuilderFactoryMock.create()).thenReturn(yotiHttpRequestBuilder);

        when(unsignedPathFactoryMock.createIdentitySessionPath()).thenReturn(SESSION_CREATION_PATH);
        when(unsignedPathFactoryMock.createIdentityMatchPath()).thenReturn(DIGITAL_ID_MATCH_PATH);
    }

    @Test
    public void createShareSession_NullSignedRequestStrategy_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareSession(null, shareSessionRequest)
        );

        assertThat(ex.getMessage(), containsString("'signedRequestStrategy' must not be null"));
    }

    @Test
    public void createShareSession_NullShareSessionRequest_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareSession(authStrategyMock, null)
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
                    () -> identityService.createShareSession(authStrategyMock, shareSessionRequest)
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

            String exMessage = "URI wrong format";
            URISyntaxException causeEx = new URISyntaxException("", exMessage);
            when(identityService.createSignedRequest(authStrategyMock, SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(authStrategyMock, shareSessionRequest)
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

            

            String exMessage = "Wrong query params format";
            UnsupportedEncodingException causeEx = new UnsupportedEncodingException(exMessage);
            when(identityService.createSignedRequest(authStrategyMock, SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(authStrategyMock, shareSessionRequest)
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

            

            String exMessage = "Wrong digest";
            GeneralSecurityException causeEx = new GeneralSecurityException(exMessage);
            when(identityService.createSignedRequest(authStrategyMock, SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.createShareSession(authStrategyMock, shareSessionRequest)
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
            
            when(identityService.createSignedRequest(authStrategyMock, SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenReturn(yotiHttpRequest);
            when(yotiHttpRequest.execute(ShareSession.class)).thenReturn(shareSession);

            ShareSession result = identityService.createShareSession(authStrategyMock, shareSessionRequest);

            assertSame(shareSession, result);
        }
    }

    @Test
    public void createShareQrCode_NullSignedRequestStrategy_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareQrCode(null, SESSION_ID)
        );

        assertThat(ex.getMessage(), containsString("'signedRequestStrategy' must not be null"));
    }

    @Test
    public void createShareQrCode_NullSessionId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.createShareQrCode(authStrategyMock, null)
        );

        assertThat(ex.getMessage(), containsString("Session ID"));
    }

    @Test
    public void fetchShareQrCode_NullSignedRequestStrategy_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchShareQrCode(null, QR_CODE_ID)
        );

        assertThat(ex.getMessage(), containsString("'signedRequestStrategy' must not be null"));
    }

    @Test
    public void fetchShareQrCode_NullSessionId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchShareQrCode(authStrategyMock, null)
        );

        assertThat(ex.getMessage(), containsString("QR Code ID"));
    }

    @Test
    public void fetchMatch_NullKeyPair_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchMatch(null, matchRequest)
        );

        assertThat(ex.getMessage(), containsString("Application Key Pair"));
    }

    @Test
    public void fetchMatch_NullMatchRequest_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> identityService.fetchMatch(authStrategyMock, null)
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
                    () -> identityService.fetchMatch(authStrategyMock, matchRequest)
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
            

            String exMessage = "URI wrong format";
            URISyntaxException causeEx = new URISyntaxException("", exMessage);
            when(identityService.createSignedRequest(authStrategyMock, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(authStrategyMock, matchRequest)
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

            

            String exMessage = "Wrong query params format";
            UnsupportedEncodingException causeEx = new UnsupportedEncodingException(exMessage);
            when(identityService.createSignedRequest(authStrategyMock, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(authStrategyMock, matchRequest)
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

            

            String exMessage = "Wrong digest";
            GeneralSecurityException causeEx = new GeneralSecurityException(exMessage);
            when(identityService.createSignedRequest(authStrategyMock, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> identityService.fetchMatch(authStrategyMock, matchRequest)
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

            
            when(identityService.createSignedRequest(authStrategyMock, DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenReturn(yotiHttpRequest);

            when(yotiHttpRequest.execute(MatchResult.class)).thenReturn(matchResult);

            MatchResult result = identityService.fetchMatch(authStrategyMock, matchRequest);
            assertSame(matchResult, result);
        }
    }

}

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

    private static final String SESSION_CREATION_PATH = "aSessionCreationPath";
    private static final String DIGITAL_ID_MATCH_PATH = "aDigitalIdMatchPath";
    private static final String POST = "POST";
    private static final byte[] A_BODY_BYTES = "aBody".getBytes(StandardCharsets.UTF_8);

    @InjectMocks DigitalIdentityService testObj;

    @Mock UnsignedPathFactory unsignedPathFactoryMock;
    @Mock YotiHttpRequestBuilderFactory requestBuilderFactoryMock;

    @Mock(answer = RETURNS_DEEP_STUBS) YotiHttpRequestBuilder yotiHttpRequestBuilder;

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
    public void createShareSession_NullShareSessionRequest_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> testObj.createShareSession(null)
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
                    () -> testObj.createShareSession(shareSessionRequest)
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
            when(testObj.createRequest(SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> testObj.createShareSession(shareSessionRequest)
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
            when(testObj.createRequest(SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> testObj.createShareSession(shareSessionRequest)
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
            when(testObj.createRequest(SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> testObj.createShareSession(shareSessionRequest)
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

            when(testObj.createRequest(SESSION_CREATION_PATH, POST, A_BODY_BYTES)).thenReturn(yotiHttpRequest);
            when(yotiHttpRequest.execute(ShareSession.class)).thenReturn(shareSession);

            ShareSession result = testObj.createShareSession(shareSessionRequest);

            assertSame(shareSession, result);
        }
    }

    @Test
    public void createShareQrCode_NullSessionId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> testObj.createShareQrCode(null)
        );

        assertThat(ex.getMessage(), containsString("Session ID"));
    }

    @Test
    public void fetchShareQrCode_NullSessionId_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> testObj.fetchShareQrCode(null)
        );

        assertThat(ex.getMessage(), containsString("QR Code ID"));
    }

    @Test
    public void fetchMatch_NullMatchRequest_Exception() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> testObj.fetchMatch(null)
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
                    () -> testObj.fetchMatch(matchRequest)
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
            when(testObj.createRequest(DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES)).thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> testObj.fetchMatch(matchRequest)
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
            when(testObj.createRequest(DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> testObj.fetchMatch(matchRequest)
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
            when(testObj.createRequest(DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenThrow(causeEx);

            DigitalIdentityException ex = assertThrows(
                    DigitalIdentityException.class,
                    () -> testObj.fetchMatch(matchRequest)
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

            when(testObj.createRequest(DIGITAL_ID_MATCH_PATH, POST, A_BODY_BYTES))
                    .thenReturn(yotiHttpRequest);
            when(yotiHttpRequest.execute(MatchResult.class)).thenReturn(matchResult);

            MatchResult result = testObj.fetchMatch(matchRequest);

            assertSame(matchResult, result);
        }
    }

}

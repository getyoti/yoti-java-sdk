package com.yoti.api.client.docs;

import static java.util.Arrays.asList;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.create.SimpleCreateSessionResult;
import com.yoti.api.client.docs.session.retrieve.*;
import com.yoti.api.client.spi.remote.call.HttpMethod;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.SignedRequestResponse;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class DocScanServiceTest {

    private static final String SOME_APP_ID = "someAppId";
    private static final String SOME_SESSION_ID = "someSessionId";
    private static final String SOME_PATH = "somePath";
    private static final String SOME_MEDIA_ID = "someMediaId";
    private static final String SOME_API_URL = System.getProperty(PROPERTY_YOTI_DOCS_URL, DEFAULT_YOTI_DOCS_URL);
    private static final byte[] SOME_SESSION_SPEC_BYTES = new byte[] { 1, 2, 3, 4 };
    private static final byte[] IMAGE_BODY = "some-image-body".getBytes();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static KeyPair KEY_PAIR;


    @Spy @InjectMocks DocScanService docScanService;

    @Mock UnsignedPathFactory unsignedPathFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock SignedRequest signedRequestMock;
    @Mock(answer = Answers.RETURNS_SELF) SignedRequestBuilder signedRequestBuilderMock;
    @Mock SignedRequestResponse signedRequestResponseMock;


    @BeforeClass
    public static void setUpClass() throws Exception {
        KEY_PAIR = generateKeyPairFrom(KEY_PAIR_PEM);
    }

    @Test
    public void createSession_shouldThrowExceptionWhenMissingAppId() throws Exception {
        try {
            docScanService.createSession(null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldThrowExceptionWhenMissingKeyPair() throws Exception {
        try {
            docScanService.createSession(SOME_APP_ID, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application key Pair"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldThrowExceptionWhenMissingSessionSpec() throws Exception {
        try {
            docScanService.createSession(SOME_APP_ID, KEY_PAIR, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionSpec"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");

        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(gse);

        try {
            docScanService.createSession(SOME_APP_ID, KEY_PAIR, sessionSpecMock);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), gse);
            assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");

        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleCreateSessionResult.class)).thenThrow(resourceException);

        try {
            docScanService.createSession(SOME_APP_ID, KEY_PAIR, sessionSpecMock);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), resourceException);
            assertThat(ex.getMessage(), containsString("Error posting the request: Failed Request"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");

        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleCreateSessionResult.class)).thenThrow(ioException);

        try {
            docScanService.createSession(SOME_APP_ID, KEY_PAIR, sessionSpecMock);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), ioException);
            assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");

        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        try {
            docScanService.createSession(SOME_APP_ID, KEY_PAIR, sessionSpecMock);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), uriSyntaxException);
            assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldWrapGeneralException() throws Exception {
        final Exception someException = new Exception("Some exception we weren't expecting");

        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock i) throws Throwable {
                throw someException;
            }
        }).when(docScanService).getSignedRequestBuilder();

        try {
            docScanService.createSession(SOME_APP_ID, KEY_PAIR, sessionSpecMock);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), someException);
            assertThat(ex.getMessage(), containsString("Error creating the session: Some exception we weren't expecting"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void createSession_shouldCallSignedRequestBuilderWithCorrectMethods() throws Exception {
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        SimpleCreateSessionResult simpleCreateSessionResultMock = mock(SimpleCreateSessionResult.class);

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(objectMapperMock.writeValueAsBytes(sessionSpecMock)).thenReturn(SOME_SESSION_SPEC_BYTES);
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleCreateSessionResult.class)).thenReturn(simpleCreateSessionResultMock);
        when(unsignedPathFactoryMock.createNewYotiDocsSessionPath(SOME_APP_ID)).thenReturn(SOME_PATH);

        CreateSessionResult result = docScanService.createSession(SOME_APP_ID, KEY_PAIR, sessionSpecMock);

        assertThat(result, Matchers.<CreateSessionResult>is(simpleCreateSessionResultMock));

        verify(signedRequestBuilderMock).withKeyPair(KEY_PAIR);
        verify(signedRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_POST);
        verify(signedRequestBuilderMock).withPayload(SOME_SESSION_SPEC_BYTES);
        verify(signedRequestBuilderMock).withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenAppIdIsNull() throws Exception {
        try {
            docScanService.retrieveSession(null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenAppIdIsEmpty() throws Exception {
        try {
            docScanService.retrieveSession("", null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenMissingKeyPair() throws Exception {
        try {
            docScanService.retrieveSession(SOME_APP_ID, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application key Pair"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenSessionIdIsNull() throws Exception {
        try {
            docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenSessionIdIsEmpty() throws Exception {
        try {
            docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, "");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(gse);

        try {
            docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), gse);
            assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleGetSessionResult.class)).thenThrow(resourceException);

        try {
            docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), resourceException);
            assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleGetSessionResult.class)).thenThrow(ioException);

        try {
            docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), ioException);
            assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldWrapGeneralException() throws Exception {
        final Exception someException = new Exception("Some exception we weren't expecting");

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock i) throws Throwable {
                throw someException;
            }
        }).when(docScanService).getSignedRequestBuilder();

        try {
            docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), someException);
            assertThat(ex.getMessage(), containsString("Error retrieving the session: Some exception we weren't expecting"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void retrieveSession_shouldCallSignedRequestBuilderWithCorrectMethods() throws Exception {
        SimpleGetSessionResult simpleDocScanSessionResponseMock = mock(SimpleGetSessionResult.class);

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute(SimpleGetSessionResult.class)).thenReturn(simpleDocScanSessionResponseMock);
        when(unsignedPathFactoryMock.createYotiDocsSessionPath(SOME_APP_ID, SOME_SESSION_ID)).thenReturn(SOME_PATH);

        SimpleGetSessionResult result = docScanService.retrieveSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);

        assertThat(result, is(simpleDocScanSessionResponseMock));

        verify(signedRequestBuilderMock).withKeyPair(KEY_PAIR);
        verify(signedRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_GET);
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenAppIdIsNull() throws Exception {
        try {
            docScanService.deleteSession(null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenAppIdIsEmpty() throws Exception {
        try {
            docScanService.deleteSession("", null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenKeyPairIsNull() throws Exception {
        try {
            docScanService.deleteSession(SOME_APP_ID, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application key Pair"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenSessionIdIsNull() throws Exception {
        try {
            docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenSessionIdIsEmpty() throws Exception {
        try {
            docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, "");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(gse);

        try {
            docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), gse);
            assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenThrow(resourceException);

        try {
            docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), resourceException);
            assertThat(ex.getMessage(), containsString("Error executing the DELETE: Failed Request"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenThrow(ioException);

        try {
            docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), ioException);
            assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldWrapGeneralException() throws Exception {
        final Exception someException = new Exception("Some exception we weren't expecting");

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock i) throws Throwable {
                throw someException;
            }
        }).when(docScanService).getSignedRequestBuilder();

        try {
            docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), someException);
            assertThat(ex.getMessage(), containsString("Error deleting the session: Some exception we weren't expecting"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteSession_shouldBuildSignedRequest() throws Exception {
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(unsignedPathFactoryMock.createYotiDocsSessionPath(SOME_APP_ID, SOME_SESSION_ID)).thenReturn(SOME_PATH);

        docScanService.deleteSession(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID);

        verify(signedRequestBuilderMock).withKeyPair(KEY_PAIR);
        verify(signedRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_DELETE);
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenApplicationIdIsNull() throws Exception {
        try {
            docScanService.getMediaContent(null, null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenApplicationIdIsEmpty() throws Exception {
        try {
            docScanService.getMediaContent("", null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenKeyPairIsNull() throws Exception {
        try {
            docScanService.getMediaContent(SOME_APP_ID, null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application key Pair"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenSessionIdIsNull() throws Exception {
        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenSessionIdIsEmpty() throws Exception {
        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, "", null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenMediaIdIsNull() throws Exception {
        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("mediaId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenMediaIdIsEmpty() throws Exception {
        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, "");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("mediaId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(gse);

        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), gse);
            assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenThrow(resourceException);

        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), resourceException);
            assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenThrow(ioException);

        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), ioException);
            assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        try {
            docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), uriSyntaxException);
            assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void getMediaContent_shouldBuildSignedRequest() throws Exception {
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        SignedRequestResponse signedRequestResponseMock = mock(SignedRequestResponse.class, RETURNS_DEEP_STUBS);
        when(signedRequestMock.execute()).thenReturn(signedRequestResponseMock);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_APP_ID, SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);

        verify(signedRequestBuilderMock).withKeyPair(KEY_PAIR);
        verify(signedRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_GET);
    }

    @Test
    public void getMediaContent_shouldReturnMedia() throws Exception {
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenReturn(signedRequestResponseMock);
        when(signedRequestResponseMock.getResponseHeaders()).thenReturn(createHeadersMap(CONTENT_TYPE, CONTENT_TYPE_JSON));
        when(signedRequestResponseMock.getResponseBody()).thenReturn(IMAGE_BODY);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_APP_ID, SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);

        assertThat(result.getMimeType(), is(CONTENT_TYPE_JSON));
        assertThat(result.getContent(), is(IMAGE_BODY));
    }

    @Test
    public void getMediaContent_shouldNotBeCaseSensitive() throws Exception {
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenReturn(signedRequestResponseMock);
        when(signedRequestResponseMock.getResponseHeaders()).thenReturn(createHeadersMap("content-type", "image/png"));
        when(signedRequestResponseMock.getResponseBody()).thenReturn(IMAGE_BODY);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_APP_ID, SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);

        assertThat(result.getMimeType(), is("image/png"));
        assertThat(result.getContent(), is(IMAGE_BODY));
    }

    private Map<String, List<String>> createHeadersMap(String key, String value) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(key, asList(value));
        return headers;
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenApplicationIdIsNull() throws Exception {
        try {
            docScanService.deleteMediaContent(null, null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenApplicationIdIsEmpty() throws Exception {
        try {
            docScanService.deleteMediaContent("", null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application id"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenKeyPairIsNull() throws Exception {
        try {
            docScanService.deleteMediaContent(SOME_APP_ID, null, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("Application key Pair"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenSessionIdIsNull() throws Exception {
        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, null, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenSessionIdIsEmpty() throws Exception {
        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, "", null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("sessionId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenMediaIdIsNull() throws Exception {
        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, null);
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("mediaId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenMediaIdIsEmpty() throws Exception {
        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, "");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("mediaId"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(gse);

        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), gse);
            assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenThrow(resourceException);

        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), resourceException);
            assertThat(ex.getMessage(), containsString("Error executing the DELETE: Failed Request"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);
        when(signedRequestMock.execute()).thenThrow(ioException);

        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), ioException);
            assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");

        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        try {
            docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);
        } catch (DocScanException ex) {
            assertSame(ex.getCause(), uriSyntaxException);
            assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void deleteMediaContent_shouldBuildSignedRequest() throws Exception {
        doReturn(signedRequestBuilderMock).when(docScanService).getSignedRequestBuilder();
        when(signedRequestBuilderMock.build()).thenReturn(signedRequestMock);

        when(unsignedPathFactoryMock.createMediaContentPath(SOME_APP_ID, SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        docScanService.deleteMediaContent(SOME_APP_ID, KEY_PAIR, SOME_SESSION_ID, SOME_MEDIA_ID);

        verify(signedRequestBuilderMock).withKeyPair(KEY_PAIR);
        verify(signedRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(signedRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(signedRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_DELETE);
    }

    @Test
    public void getSignedRequestBuilder_shouldReturnNewInstance() {
        SignedRequestBuilder firstSignedRequestBuilder = docScanService.getSignedRequestBuilder();
        SignedRequestBuilder secondSignedRequestBuilder = docScanService.getSignedRequestBuilder();

        assertNotSame(firstSignedRequestBuilder, secondSignedRequestBuilder);
    }

    @Test
    public void shouldNotFailForUnknownChecks() throws Exception {
        InputStream is = getClass().getResourceAsStream("/GetSessionResultExample.json");
        GetSessionResult result = MAPPER.readValue(is, SimpleGetSessionResult.class);

        assertThat(result.getChecks(), hasSize(2));

        assertThat(result.getChecks().get(0), is(instanceOf(SimpleCheckResponse.class)));
        assertThat(result.getChecks().get(0), is(not(instanceOf(SimpleAuthenticityCheckResponse.class))));
        assertThat(result.getChecks().get(0).getId(), is("someUnknownCheckId"));

        assertThat(result.getChecks().get(1), is(instanceOf(SimpleAuthenticityCheckResponse.class)));
        assertThat(result.getChecks().get(1).getId(), is("documentAuthenticityCheckId"));

        List<? extends LivenessResourceResponse> livenessResourceResponse = result.getResources().getLivenessCapture();
        assertThat(livenessResourceResponse.get(0), is(instanceOf(SimpleLivenessResourceResponse.class)));
        assertThat(livenessResourceResponse.get(0).getId(), is("someUnknownLivenessId"));

        assertThat(livenessResourceResponse.get(1), is(instanceOf(SimpleZoomLivenessResourceResponse.class)));
        assertThat(livenessResourceResponse.get(1).getId(), is("someZoomId"));
    }

}

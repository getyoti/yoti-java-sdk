package com.yoti.api.client.docs;

import static java.util.Arrays.asList;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;

import static junit.framework.TestCase.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.create.facecapture.CreateFaceCaptureResourcePayload;
import com.yoti.api.client.docs.session.create.facecapture.UploadFaceCaptureImagePayload;
import com.yoti.api.client.docs.session.devicemetadata.MetadataResponse;
import com.yoti.api.client.docs.session.instructions.Instructions;
import com.yoti.api.client.docs.session.retrieve.AuthenticityCheckResponse;
import com.yoti.api.client.docs.session.retrieve.CheckResponse;
import com.yoti.api.client.docs.session.retrieve.CreateFaceCaptureResourceResponse;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.docs.session.retrieve.LivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.ZoomLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.SessionConfigurationResponse;
import com.yoti.api.client.docs.session.retrieve.instructions.ContactProfileResponse;
import com.yoti.api.client.docs.session.retrieve.instructions.InstructionsResponse;
import com.yoti.api.client.docs.support.SupportedDocumentsResponse;
import com.yoti.api.client.spi.remote.call.HttpMethod;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilder;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.YotiHttpResponse;
import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DocScanServiceTest {

    private static final String SOME_SESSION_ID = "someSessionId";
    private static final String SOME_PATH = "somePath";
    private static final String SOME_MEDIA_ID = "someMediaId";
    private static final String SOME_API_URL = System.getProperty(PROPERTY_YOTI_DOCS_URL, DEFAULT_YOTI_DOCS_URL);
    private static final String SOME_RESOURCE_ID = "someResourceId";
    private static final String SOME_IMAGE_CONTENT_TYPE = "someImageContentType";
    private static final byte[] SOME_SESSION_SPEC_BYTES = new byte[] { 1, 2, 3, 4 };
    private static final byte[] IMAGE_BODY = "some-image-body".getBytes();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Spy @InjectMocks DocScanService docScanService;

    @Mock UnsignedPathFactory unsignedPathFactoryMock;
    @Mock ObjectMapper objectMapperMock;
    @Mock(answer = Answers.RETURNS_SELF) YotiHttpRequestBuilder yotiHttpRequestBuilderMock;

    @Mock AuthStrategy authStrategyMock;
    @Mock YotiHttpRequest yotiHttpRequestMock;
    @Mock YotiHttpResponse yotiHttpResponseMock;
    @Mock YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactoryMock;
    @Mock SupportedDocumentsResponse supportedDocumentsResponseMock;
    @Mock Instructions instructionsMock;
    @Mock CreateFaceCaptureResourcePayload createFaceCaptureResourcePayloadMock;
    @Mock UploadFaceCaptureImagePayload uploadFaceCaptureImagePayloadMock;

    @Before
    public void setUp() {
        when(yotiHttpRequestBuilderFactoryMock.create()).thenReturn(yotiHttpRequestBuilderMock);
    }

    @Test
    public void createSession_shouldThrowExceptionWhenMissingAuthStrategy() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.createSession(null, null));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void createSession_shouldThrowExceptionWhenMissingSessionSpec() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.createSession(authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionSpec"));
    }

    @Test
    public void createSession_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createSession(authStrategyMock, sessionSpecMock));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void createSession_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(CreateSessionResult.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createSession(authStrategyMock, sessionSpecMock));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error posting the request: Failed Request"));
    }

    @Test
    public void createSession_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(CreateSessionResult.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createSession(authStrategyMock, sessionSpecMock));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void createSession_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createSession(authStrategyMock, sessionSpecMock));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));

    }

    @Test
    public void createSession_shouldWrapGeneralException() {
        Exception someException = new Exception("Some exception we weren't expecting");
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        doAnswer(i -> {throw someException;}).when(yotiHttpRequestBuilderFactoryMock).create();

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createSession(authStrategyMock, sessionSpecMock));

        assertSame(ex.getCause(), someException);
        assertThat(ex.getMessage(), containsString("Error creating the session: Some exception we weren't expecting"));
    }

    @Test
    public void createSession_shouldCallSignedRequestBuilderWithCorrectMethods() throws Exception {
        SessionSpec sessionSpecMock = mock(SessionSpec.class);
        CreateSessionResult createSessionResultMock = mock(CreateSessionResult.class);
        when(objectMapperMock.writeValueAsBytes(sessionSpecMock)).thenReturn(SOME_SESSION_SPEC_BYTES);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(CreateSessionResult.class)).thenReturn(createSessionResultMock);
        when(unsignedPathFactoryMock.createNewYotiDocsSessionPath()).thenReturn(SOME_PATH);

        CreateSessionResult result = docScanService.createSession(authStrategyMock, sessionSpecMock);

        assertThat(result, is(createSessionResultMock));
        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_POST);
        verify(yotiHttpRequestBuilderMock).withPayload(SOME_SESSION_SPEC_BYTES);
        verify(yotiHttpRequestBuilderMock).withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenMissingAuthStrategy() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.retrieveSession(null, null));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.retrieveSession(authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void retrieveSession_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.retrieveSession(authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void retrieveSession_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.retrieveSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void retrieveSession_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(GetSessionResult.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.retrieveSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void retrieveSession_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(GetSessionResult.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.retrieveSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void retrieveSession_shouldWrapGeneralException() {
        Exception someException = new Exception("Some exception we weren't expecting");
        doAnswer(i -> {throw someException;}).when(yotiHttpRequestBuilderFactoryMock).create();

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.retrieveSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), someException);
        assertThat(ex.getMessage(), containsString("Error retrieving the session: Some exception we weren't expecting"));
    }

    @Test
    public void retrieveSession_shouldCallSignedRequestBuilderWithCorrectMethods() throws Exception {
        GetSessionResult docScanSessionResponseMock = mock(GetSessionResult.class);
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(GetSessionResult.class)).thenReturn(docScanSessionResponseMock);
        when(unsignedPathFactoryMock.createYotiDocsSessionPath(SOME_SESSION_ID)).thenReturn(SOME_PATH);

        GetSessionResult result = docScanService.retrieveSession(authStrategyMock, SOME_SESSION_ID);

        assertThat(result, is(docScanSessionResponseMock));
        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_GET);
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteSession(null, null));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteSession(authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void deleteSession_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteSession(authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void deleteSession_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void deleteSession_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the DELETE: Failed Request"));
    }

    @Test
    public void deleteSession_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void deleteSession_shouldWrapGeneralException() {
        Exception someException = new Exception("Some exception we weren't expecting");
        doAnswer(i -> {throw someException;}).when(yotiHttpRequestBuilderFactoryMock).create();

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteSession(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), someException);
        assertThat(ex.getMessage(), containsString("Error deleting the session: Some exception we weren't expecting"));
    }

    @Test
    public void deleteSession_shouldBuildSignedRequest() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(unsignedPathFactoryMock.createYotiDocsSessionPath(SOME_SESSION_ID)).thenReturn(SOME_PATH);

        docScanService.deleteSession(authStrategyMock, SOME_SESSION_ID);

        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_DELETE);
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getMediaContent(null, null, null));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getMediaContent(authStrategyMock, null, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getMediaContent(authStrategyMock, "", null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenMediaIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, null));

        assertThat(ex.getMessage(), containsString("mediaId"));
    }

    @Test
    public void getMediaContent_shouldThrowExceptionWhenMediaIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, ""));

        assertThat(ex.getMessage(), containsString("mediaId"));
    }

    @Test
    public void getMediaContent_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void getMediaContent_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void getMediaContent_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void getMediaContent_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getMediaContent_shouldBuildSignedRequest() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        YotiHttpResponse yotiHttpResponseMock = mock(YotiHttpResponse.class, RETURNS_DEEP_STUBS);
        when(yotiHttpRequestMock.execute()).thenReturn(yotiHttpResponseMock);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID);

        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_GET);
    }

    @Test
    public void getMediaContent_shouldReturnMedia() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenReturn(yotiHttpResponseMock);
        when(yotiHttpResponseMock.getResponseHeaders()).thenReturn(createHeadersMap(CONTENT_TYPE, CONTENT_TYPE_JSON));
        when(yotiHttpResponseMock.getResponseBody()).thenReturn(IMAGE_BODY);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID);

        assertThat(result.getMimeType(), is(CONTENT_TYPE_JSON));
        assertThat(result.getContent(), is(IMAGE_BODY));
    }

    @Test
    public void getMediaContent_shouldReturnNullForNoContent() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenReturn(yotiHttpResponseMock);
        when(yotiHttpResponseMock.getResponseCode()).thenReturn(204);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void getMediaContent_shouldNotBeCaseSensitive() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenReturn(yotiHttpResponseMock);
        when(yotiHttpResponseMock.getResponseHeaders()).thenReturn(createHeadersMap("content-type", "image/png"));
        when(yotiHttpResponseMock.getResponseBody()).thenReturn(IMAGE_BODY);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID);

        assertThat(result.getMimeType(), is("image/png"));
        assertThat(result.getContent(), is(IMAGE_BODY));
    }

    private Map<String, List<String>> createHeadersMap(String key, String value) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(key, asList(value));
        return headers;
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteMediaContent(null, null, null));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteMediaContent(authStrategyMock, null, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteMediaContent(authStrategyMock, "", null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenMediaIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, null));

        assertThat(ex.getMessage(), containsString("mediaId"));
    }

    @Test
    public void deleteMediaContent_shouldThrowExceptionWhenMediaIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, ""));

        assertThat(ex.getMessage(), containsString("mediaId"));
    }

    @Test
    public void deleteMediaContent_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void deleteMediaContent_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the DELETE: Failed Request"));
    }

    @Test
    public void deleteMediaContent_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void deleteMediaContent_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void deleteMediaContent_shouldBuildSignedRequest() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(unsignedPathFactoryMock.createMediaContentPath(SOME_SESSION_ID, SOME_MEDIA_ID)).thenReturn(SOME_PATH);

        docScanService.deleteMediaContent(authStrategyMock, SOME_SESSION_ID, SOME_MEDIA_ID);

        verify(yotiHttpRequestBuilderMock).withAuthStrategy(authStrategyMock);
        verify(yotiHttpRequestBuilderMock).withEndpoint(SOME_PATH);
        verify(yotiHttpRequestBuilderMock).withBaseUrl(SOME_API_URL);
        verify(yotiHttpRequestBuilderMock).withHttpMethod(HttpMethod.HTTP_DELETE);
    }

    @Test
    public void shouldNotFailForUnknownChecks() throws Exception {
        InputStream is = getClass().getResourceAsStream("/GetSessionResultExample.json");
        GetSessionResult result = MAPPER.readValue(is, GetSessionResult.class);

        assertThat(result.getChecks(), hasSize(2));

        assertThat(result.getChecks().get(0), is(instanceOf(CheckResponse.class)));
        assertThat(result.getChecks().get(0), is(not(instanceOf(AuthenticityCheckResponse.class))));
        assertThat(result.getChecks().get(0).getId(), is("someUnknownCheckId"));

        assertThat(result.getChecks().get(1), is(instanceOf(AuthenticityCheckResponse.class)));
        assertThat(result.getChecks().get(1).getId(), is("documentAuthenticityCheckId"));

        List<? extends LivenessResourceResponse> livenessResourceResponse = result.getResources().getLivenessCapture();
        assertThat(livenessResourceResponse.get(0), is(instanceOf(LivenessResourceResponse.class)));
        assertThat(livenessResourceResponse.get(0).getId(), is("someUnknownLivenessId"));

        assertThat(livenessResourceResponse.get(1), is(instanceOf(ZoomLivenessResourceResponse.class)));
        assertThat(livenessResourceResponse.get(1).getId(), is("someZoomId"));
    }

    @Test
    public void putIbvInstructions_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.putIbvInstructions(null, SOME_SESSION_ID, instructionsMock));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void putIbvInstructions_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.putIbvInstructions(authStrategyMock, null, instructionsMock));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void putIbvInstructions_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.putIbvInstructions(authStrategyMock, "", instructionsMock));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void putIbvInstructions_shouldThrowExceptionWhenInstructionsIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.putIbvInstructions(authStrategyMock, SOME_SESSION_ID, null));

        assertThat(ex.getMessage(), containsString("instructions"));
    }

    @Test
    public void putIbvInstructions_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.putIbvInstructions(authStrategyMock, SOME_SESSION_ID, instructionsMock));

        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void putIbvInstructions_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.putIbvInstructions(authStrategyMock, SOME_SESSION_ID, instructionsMock));

        assertThat(ex.getMessage(), containsString("Error executing the PUT: Failed Request"));
    }

    @Test
    public void putIbvInstructions_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.putIbvInstructions(authStrategyMock, SOME_SESSION_ID, instructionsMock));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void putIbvInstructions_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.putIbvInstructions(authStrategyMock, SOME_SESSION_ID, instructionsMock));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getIbvInstructions_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getIbvInstructions(null, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void getIbvInstructions_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getIbvInstructions(authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getIbvInstructions_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getIbvInstructions(authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getIbvInstructions_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructions(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void getIbvInstructions_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(InstructionsResponse.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructions(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void getIbvInstructions_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(InstructionsResponse.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructions(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void getIbvInstructions_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructions(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getIbvInstructionsPdf(null, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void getIbvInstructionsPdf_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getIbvInstructionsPdf(
                authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getIbvInstructionsPdf(
                authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getIbvInstructionsPdf_shouldReturnMedia() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenReturn(yotiHttpResponseMock);
        when(yotiHttpResponseMock.getResponseHeaders()).thenReturn(createHeadersMap(CONTENT_TYPE, CONTENT_TYPE_JSON));
        when(yotiHttpResponseMock.getResponseBody()).thenReturn(IMAGE_BODY);
        when(unsignedPathFactoryMock.createFetchIbvInstructionsPdfPath(SOME_SESSION_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID);

        assertThat(result.getMimeType(), is(CONTENT_TYPE_JSON));
        assertThat(result.getContent(), is(IMAGE_BODY));
    }

    @Test
    public void getIbvInstructionsPdf_shouldReturnNullForNoContent() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenReturn(yotiHttpResponseMock);
        when(yotiHttpResponseMock.getResponseCode()).thenReturn(204);
        when(unsignedPathFactoryMock.createFetchIbvInstructionsPdfPath(SOME_SESSION_ID)).thenReturn(SOME_PATH);

        Media result = docScanService.getIbvInstructionsPdf(authStrategyMock, SOME_SESSION_ID);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.fetchInstructionsContactProfile(null, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.fetchInstructionsContactProfile(
                authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.fetchInstructionsContactProfile(
                authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchInstructionsContactProfile(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(ContactProfileResponse.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchInstructionsContactProfile(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(ContactProfileResponse.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchInstructionsContactProfile(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void fetchInstructionsContactProfile_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchInstructionsContactProfile(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void fetchSessionConfiguration_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.fetchSessionConfiguration(null, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void fetchSessionConfiguration_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.fetchSessionConfiguration(
                authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void fetchSessionConfiguration_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.fetchSessionConfiguration(
                authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void fetchSessionConfiguration_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchSessionConfiguration(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void fetchSessionConfiguration_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(SessionConfigurationResponse.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchSessionConfiguration(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void fetchSessionConfiguration_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(SessionConfigurationResponse.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchSessionConfiguration(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void fetchSessionConfiguration_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.fetchSessionConfiguration(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void createFaceCaptureResource_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.createFaceCaptureResource(null, SOME_SESSION_ID, createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void createFaceCaptureResource_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.createFaceCaptureResource(
                authStrategyMock, null, createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void createFaceCaptureResource_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.createFaceCaptureResource(
                authStrategyMock, "", createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void createFaceCaptureResource_shouldThrowExceptionWhenPayloadIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.createFaceCaptureResource(
                authStrategyMock, SOME_SESSION_ID, null));

        assertThat(ex.getMessage(), containsString("createFaceCaptureResourcePayload"));
    }

    @Test
    public void createFaceCaptureResource_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createFaceCaptureResource(authStrategyMock, SOME_SESSION_ID, createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("Error signing the request: some gse"));
    }

    @Test
    public void createFaceCaptureResource_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(CreateFaceCaptureResourceResponse.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createFaceCaptureResource(authStrategyMock, SOME_SESSION_ID, createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("Error executing the POST: Failed Request"));
    }

    @Test
    public void createFaceCaptureResource_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(CreateFaceCaptureResourceResponse.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createFaceCaptureResource(authStrategyMock, SOME_SESSION_ID, createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void createFaceCaptureResource_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.createFaceCaptureResource(authStrategyMock, SOME_SESSION_ID, createFaceCaptureResourcePayloadMock));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldFailForNullAuthStrategy() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.uploadFaceCaptureImage(null, null, null, null));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void uploadFaceCaptureImage_shouldFailForNullSessionId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.uploadFaceCaptureImage(
                authStrategyMock, null, null,null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldFailForEmptySessionId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.uploadFaceCaptureImage(
                authStrategyMock, "", null, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldFailForNullResourceId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.uploadFaceCaptureImage(
                authStrategyMock, SOME_SESSION_ID, null,null));

        assertThat(ex.getMessage(), containsString("resourceId"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldFailForEmptyResourceId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.uploadFaceCaptureImage(
                authStrategyMock, SOME_SESSION_ID, "", null));

        assertThat(ex.getMessage(), containsString("resourceId"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldFailForNullPayload() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.uploadFaceCaptureImage(
                authStrategyMock, SOME_SESSION_ID, SOME_RESOURCE_ID, null));

        assertThat(ex.getMessage(), containsString("faceCaptureImagePayload"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldWrapGeneralSecurityException() throws Exception {
        when(uploadFaceCaptureImagePayloadMock.getImageContents()).thenReturn(IMAGE_BODY);
        when(uploadFaceCaptureImagePayloadMock.getImageContentType()).thenReturn(SOME_IMAGE_CONTENT_TYPE);
        when(unsignedPathFactoryMock.createUploadFaceCaptureImagePath(SOME_SESSION_ID, SOME_RESOURCE_ID)).thenReturn(SOME_PATH);
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.uploadFaceCaptureImage(authStrategyMock, SOME_SESSION_ID, SOME_RESOURCE_ID, uploadFaceCaptureImagePayloadMock));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error executing the PUT: some gse"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldWrapURISyntaxException() throws Exception {
        when(uploadFaceCaptureImagePayloadMock.getImageContents()).thenReturn(IMAGE_BODY);
        when(uploadFaceCaptureImagePayloadMock.getImageContentType()).thenReturn(SOME_IMAGE_CONTENT_TYPE);
        when(unsignedPathFactoryMock.createUploadFaceCaptureImagePath(SOME_SESSION_ID, SOME_RESOURCE_ID)).thenReturn(SOME_PATH);
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.uploadFaceCaptureImage(authStrategyMock, SOME_SESSION_ID, SOME_RESOURCE_ID, uploadFaceCaptureImagePayloadMock));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldWrapIOException() throws Exception {
        when(uploadFaceCaptureImagePayloadMock.getImageContents()).thenReturn(IMAGE_BODY);
        when(uploadFaceCaptureImagePayloadMock.getImageContentType()).thenReturn(SOME_IMAGE_CONTENT_TYPE);
        when(unsignedPathFactoryMock.createUploadFaceCaptureImagePath(SOME_SESSION_ID, SOME_RESOURCE_ID)).thenReturn(SOME_PATH);
        IOException ioException = new IOException("some IO exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.uploadFaceCaptureImage(authStrategyMock, SOME_SESSION_ID, SOME_RESOURCE_ID, uploadFaceCaptureImagePayloadMock));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: some IO exception"));
    }

    @Test
    public void uploadFaceCaptureImage_shouldWrapResourceException() throws Exception {
        when(uploadFaceCaptureImagePayloadMock.getImageContents()).thenReturn(IMAGE_BODY);
        when(uploadFaceCaptureImagePayloadMock.getImageContentType()).thenReturn(SOME_IMAGE_CONTENT_TYPE);
        when(unsignedPathFactoryMock.createUploadFaceCaptureImagePath(SOME_SESSION_ID, SOME_RESOURCE_ID)).thenReturn(SOME_PATH);
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.uploadFaceCaptureImage(authStrategyMock, SOME_SESSION_ID, SOME_RESOURCE_ID, uploadFaceCaptureImagePayloadMock));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the PUT: Failed Request"));
    }

    @Test
    public void triggerIbvEmailNotification_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.triggerIbvEmailNotification(null, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void triggerIbvEmailNotification_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.triggerIbvEmailNotification(
                authStrategyMock, null));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void triggerIbvEmailNotification_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.triggerIbvEmailNotification(
                authStrategyMock, ""));

        assertThat(ex.getMessage(), containsString("sessionId"));
    }

    @Test
    public void triggerIbvEmailNotification_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.triggerIbvEmailNotification(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error executing the POST: some gse"));
    }

    @Test
    public void triggerIbvEmailNotification_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.triggerIbvEmailNotification(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error executing the POST: Failed Request"));
    }

    @Test
    public void triggerIbvEmailNotification_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.triggerIbvEmailNotification(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void triggerIbvEmailNotification_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.triggerIbvEmailNotification(authStrategyMock, SOME_SESSION_ID));

        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getSupportedDocuments_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> docScanService.getSupportedDocuments(null, false));

        assertThat(ex.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void getSupportedDocuments_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getSupportedDocuments(authStrategyMock, false));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error executing the GET: some gse"));
    }

    @Test
    public void getSupportedDocuments_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(SupportedDocumentsResponse.class)).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getSupportedDocuments(authStrategyMock, false));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void getSupportedDocuments_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(SupportedDocumentsResponse.class)).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getSupportedDocuments(authStrategyMock, false));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void getSupportedDocuments_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getSupportedDocuments(authStrategyMock, false));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getSupportedDocuments_shouldReturnSupportedDocuments() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(SupportedDocumentsResponse.class)).thenReturn(supportedDocumentsResponseMock);
        when(unsignedPathFactoryMock.createGetSupportedDocumentsPath(false)).thenReturn(SOME_PATH);

        SupportedDocumentsResponse result = docScanService.getSupportedDocuments(authStrategyMock, false);

        assertThat(result, is(instanceOf(SupportedDocumentsResponse.class)));
    }

    @Test
    public void getTrackedDevices_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> docScanService.getTrackedDevices(null, SOME_SESSION_ID));
        assertThat(exception.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void getTrackedDevices_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> docScanService.getTrackedDevices(
                authStrategyMock, null));
        assertThat(exception.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getTrackedDevices_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> docScanService.getTrackedDevices(
                authStrategyMock, ""));
        assertThat(exception.getMessage(), containsString("sessionId"));
    }

    @Test
    public void getTrackedDevices_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error executing the GET: some gse"));
    }

    @Test
    public void getTrackedDevices_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(ArgumentMatchers.any(TypeReference.class))).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void getTrackedDevices_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute(ArgumentMatchers.any(TypeReference.class))).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void getTrackedDevices_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.getTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

    @Test
    public void getTrackedDevices_shouldReturnTrackedDevices() throws Exception {
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        MetadataResponse metadataResponseMock = mock(MetadataResponse.class);
        when(yotiHttpRequestMock.execute(ArgumentMatchers.any(TypeReference.class))).thenReturn(Collections.singletonList(metadataResponseMock));
        when(unsignedPathFactoryMock.createFetchTrackedDevices(SOME_SESSION_ID)).thenReturn(SOME_PATH);

        List<MetadataResponse> result = docScanService.getTrackedDevices(authStrategyMock, SOME_SESSION_ID);

        assertThat(result.get(0), is(metadataResponseMock));
    }

    @Test
    public void deleteTrackedDevices_shouldThrowExceptionWhenAuthStrategyIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteTrackedDevices(null, SOME_SESSION_ID));
        assertThat(exception.getMessage(), containsString("'authStrategy' must not be null."));
    }

    @Test
    public void deleteTrackedDevices_shouldThrowExceptionWhenSessionIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteTrackedDevices(
                authStrategyMock, null));
        assertThat(exception.getMessage(), containsString("sessionId"));
    }

    @Test
    public void deleteTrackedDevices_shouldThrowExceptionWhenSessionIdIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> docScanService.deleteTrackedDevices(
                authStrategyMock, ""));
        assertThat(exception.getMessage(), containsString("sessionId"));
    }

    @Test
    public void deleteTrackedDevices_shouldWrapGeneralSecurityException() throws Exception {
        GeneralSecurityException gse = new GeneralSecurityException("some gse");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(gse);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), gse);
        assertThat(ex.getMessage(), containsString("Error executing the GET: some gse"));
    }

    @Test
    public void deleteTrackedDevices_shouldWrapResourceException() throws Exception {
        ResourceException resourceException = new ResourceException(400, "Failed Request", "Some response from API");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(resourceException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), resourceException);
        assertThat(ex.getMessage(), containsString("Error executing the GET: Failed Request"));
    }

    @Test
    public void deleteTrackedDevices_shouldWrapIOException() throws Exception {
        IOException ioException = new IOException("Some io exception");
        when(yotiHttpRequestBuilderMock.build()).thenReturn(yotiHttpRequestMock);
        when(yotiHttpRequestMock.execute()).thenThrow(ioException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), ioException);
        assertThat(ex.getMessage(), containsString("Error building the request: Some io exception"));
    }

    @Test
    public void deleteTrackedDevices_shouldWrapURISyntaxException() throws Exception {
        URISyntaxException uriSyntaxException = new URISyntaxException("someUrl", "Failed to build URI");
        when(yotiHttpRequestBuilderMock.build()).thenThrow(uriSyntaxException);

        DocScanException ex = assertThrows(DocScanException.class, () -> docScanService.deleteTrackedDevices(authStrategyMock, SOME_SESSION_ID));

        assertSame(ex.getCause(), uriSyntaxException);
        assertThat(ex.getMessage(), containsString("Error building the request: Failed to build URI: someUrl"));
    }

}

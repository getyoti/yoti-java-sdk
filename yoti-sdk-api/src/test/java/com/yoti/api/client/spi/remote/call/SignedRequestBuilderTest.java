package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class SignedRequestBuilderTest {

    private static final String SOME_ENDPOINT = "/someEndpoint";
    private static final String SOME_SIGNATURE = "someSignature";
    private static final Map<String, String> SIGNED_REQUEST_HEADERS;
    private static final String SOME_BASE_URL = "someBaseUrl";
    private static final byte[] SOME_BYTES = "someBytes".getBytes();
    private static final String SIGNATURE_PARAMS_STRING = "nonce=someNonce&timestamp=someTimestamp";
    private static final String SOME_MULTIPART_BODY_NAME = "someMultipartBodyName";
    private static final byte[] SOME_MULTIPART_BODY = new byte[]{ 1, 2, 3, 4 };
    private static final ContentType SOME_MULTIPART_CONTENT_TYPE = ContentType.IMAGE_JPEG;
    private static final String SOME_MULTIPART_FILE_NAME = "someMultipartFileName";
    private static final String SOME_MULTIPART_BOUNDARY = "someMultipartBoundary";
    private static final byte[] SOME_BUILT_MULTIPART_BODY = new byte[]{ 5, 6, 7, 8 };
    private static KeyPair KEY_PAIR;

    static {
        Security.addProvider(new BouncyCastleProvider());

        SIGNED_REQUEST_HEADERS = new HashMap<>();
        SIGNED_REQUEST_HEADERS.put(YotiConstants.DIGEST_HEADER, SOME_SIGNATURE);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        KEY_PAIR = generateKeyPairFrom(KEY_PAIR_PEM);
    }

    @InjectMocks SignedRequestBuilder signedRequestBuilder;

    @Mock PathFactory pathFactoryMock;
    @Mock SignedMessageFactory signedMessageFactoryMock;
    @Mock HeadersFactory headersFactoryMock;
    @Mock MultipartEntityBuilder multipartEntityBuilderMock;
    @Captor ArgumentCaptor<String> pathCaptor;

    @Mock(answer = Answers.RETURNS_SELF) HttpEntity httpEntityMock;
    @Mock Header headerMock;
    @Mock HeaderElement headerElementMock;

    @Test
    public void build_shouldThrowExceptionWhenMissingKeyPair() throws Exception {
        try {
            signedRequestBuilder.build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("keyPair"));
            return;
        }
        fail("Expected an IllegalArgumentException");
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingBaseUrl() throws Exception {
        try {
            signedRequestBuilder.withKeyPair(KEY_PAIR)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("baseUrl"));
            return;
        }
        fail("Expected an IllegalArgumentException");
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingEndpoint() throws Exception {
        try {
            signedRequestBuilder.withKeyPair(KEY_PAIR)
                    .withBaseUrl(SOME_BASE_URL)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("endpoint"));
            return;
        }
        fail("Expected an IllegalArgumentException");
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingHttpMethod() throws Exception {
        try {
            signedRequestBuilder.withKeyPair(KEY_PAIR)
                    .withBaseUrl(SOME_BASE_URL)
                    .withEndpoint(SOME_ENDPOINT)
                    .build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("httpMethod"));
            return;
        }
        fail("Expected an IllegalArgumentException");
    }

    @Test
    public void build_shouldCreateSignedRequestWithCustomQueryParameter() throws Exception {
        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);

        SignedRequest result = signedRequestBuilder.withKeyPair(KEY_PAIR)
                .withBaseUrl(SOME_BASE_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HttpMethod.GET)
                .withQueryParameter("someQueryParam", "someParamValue")
                .build();

        assertThat(result.getUri().getQuery(), containsString(SIGNATURE_PARAMS_STRING));
    }

    @Test
    public void build_shouldCreateSignedRequestWithProvidedHttpHeader() throws Exception {
        SignedRequest result = signedRequestBuilder.withKeyPair(KEY_PAIR)
                .withBaseUrl(DEFAULT_YOTI_API_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HttpMethod.GET)
                .withHeader("myCustomHeader", "customHeaderValue")
                .build();

        assertThat(result.getHeaders(), hasEntry("myCustomHeader", "customHeaderValue"));
    }

    @Test
    public void build_shouldIgnoreAnyProvidedSignedRequestHeaders() throws Exception {
        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);
        when(signedMessageFactoryMock.create(any(PrivateKey.class), any(HttpMethod.class), anyString())).thenReturn(SOME_SIGNATURE);
        when(headersFactoryMock.create(SOME_SIGNATURE)).thenReturn(SIGNED_REQUEST_HEADERS);

        SignedRequest result = signedRequestBuilder.withKeyPair(KEY_PAIR)
                .withBaseUrl(DEFAULT_YOTI_API_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HttpMethod.GET)
                .withHeader(DIGEST_HEADER, "customHeaderValue")
                .build();

        assertThat(result.getHeaders(), hasEntry(DIGEST_HEADER, SOME_SIGNATURE));
    }

    @Test
    public void withBoundary_shouldThrowWhenBoundaryIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBoundary(null));

        assertThat(ex.getMessage(), containsString("multipartBoundary"));
    }

    @Test
    public void withBoundary_shouldThrowWhenBoundaryIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBoundary(""));

        assertThat(ex.getMessage(), containsString("multipartBoundary"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenNameIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBinaryBody(null, null, null, null));

        assertThat(ex.getMessage(), containsString("name"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenNameIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBinaryBody("", null, null, null));

        assertThat(ex.getMessage(), containsString("name"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenPayloadIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, null, null, null));

        assertThat(ex.getMessage(), containsString("payload"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenContentTypeIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, null, null));

        assertThat(ex.getMessage(), containsString("contentType"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenFileNameIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, null));

        assertThat(ex.getMessage(), containsString("fileName"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenFileNameIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> signedRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, ""));

        assertThat(ex.getMessage(), containsString("fileName"));
    }

    @Test
    public void shouldRemoveTrailingSlashesFromBaseUrl() throws Exception {
        SignedRequest simpleSignedRequest = signedRequestBuilder.withKeyPair(KEY_PAIR)
                .withBaseUrl(SOME_BASE_URL + "////////////")
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HttpMethod.GET)
                .build();

        assertThat(simpleSignedRequest.getUri().toString(), containsString(SOME_BASE_URL + SOME_ENDPOINT));
    }

    @Test
    public void shouldCreateSignedRequestSuccessfullyWithRequiredFieldsOnly() throws Exception {
        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);
        when(signedMessageFactoryMock.create(any(PrivateKey.class), any(HttpMethod.class), anyString())).thenReturn(SOME_SIGNATURE);
        when(headersFactoryMock.create(SOME_SIGNATURE)).thenReturn(SIGNED_REQUEST_HEADERS);

        SignedRequest result = signedRequestBuilder.withKeyPair(KEY_PAIR)
                .withBaseUrl(SOME_BASE_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HttpMethod.GET)
                .build();

        verify(signedMessageFactoryMock).create(eq(KEY_PAIR.getPrivate()), eq(HttpMethod.GET), pathCaptor.capture());
        assertThat(pathCaptor.getValue(), is(SOME_ENDPOINT + "?" + SIGNATURE_PARAMS_STRING));
        assertThat(result.getUri().toString(), is(SOME_BASE_URL + pathCaptor.getValue()));
        assertThat(result.getMethod(), is(HttpMethod.GET));
        assertThat(result.getHeaders().get(DIGEST_HEADER), containsString(SOME_SIGNATURE));
        assertThat(result.getHeaders(), hasEntry(DIGEST_HEADER, SOME_SIGNATURE));
        assertNull(result.getData());
    }

    @Test
    public void shouldCreatedSignedRequestSuccessfullyWithAllProperties() throws Exception {
        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);
        when(signedMessageFactoryMock.create(any(PrivateKey.class), any(HttpMethod.class), anyString(), any(byte[].class))).thenReturn(SOME_SIGNATURE);
        when(headersFactoryMock.create(SOME_SIGNATURE)).thenReturn(SIGNED_REQUEST_HEADERS);

        SignedRequest result = signedRequestBuilder.withKeyPair(KEY_PAIR)
                .withBaseUrl(SOME_BASE_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HttpMethod.GET)
                .withQueryParameter("someQueryParam", "someParamValue")
                .withHeader("myCustomHeader", "customHeaderValue")
                .withPayload(SOME_BYTES)
                .build();

        verify(signedMessageFactoryMock).create(eq(KEY_PAIR.getPrivate()), eq(HttpMethod.GET), pathCaptor.capture(), eq(SOME_BYTES));
        assertThat(pathCaptor.getValue(), is(SOME_ENDPOINT + "?someQueryParam=someParamValue&" + SIGNATURE_PARAMS_STRING));
        assertThat(result.getUri().toString(), is(SOME_BASE_URL + pathCaptor.getValue()));
        assertThat(result.getMethod(), is(HttpMethod.GET));
        assertThat(result.getHeaders().get(DIGEST_HEADER), containsString(SOME_SIGNATURE));
        assertThat(result.getHeaders(), hasEntry(DIGEST_HEADER, SOME_SIGNATURE));
        assertThat(result.getHeaders(), hasEntry("myCustomHeader", "customHeaderValue"));
        assertArrayEquals(SOME_BYTES, result.getData());
    }

    @Test
    public void shouldSetContentTypeToMultipartWhenUserHasSetRequestTypeToMultipart() throws Exception {
        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);
        when(signedMessageFactoryMock.create(any(PrivateKey.class), any(HttpMethod.class), anyString(), any(byte[].class))).thenReturn(SOME_SIGNATURE);
        when(headersFactoryMock.create(SOME_SIGNATURE)).thenReturn(SIGNED_REQUEST_HEADERS);
        when(multipartEntityBuilderMock.build()).thenReturn(httpEntityMock);

        when(headerElementMock.toString()).thenReturn(SOME_MULTIPART_CONTENT_TYPE.toString());
        when(headerMock.getElements()).thenReturn(new HeaderElement[]{ headerElementMock });
        when(httpEntityMock.getContentType()).thenReturn(headerMock);

        // This is to overcome where we can't mock the OutputStream
        // in the getMultipartBodyAsBytes() method
        doAnswer((Answer<Void>) invocation -> {
            Object[] args = invocation.getArguments();
            OutputStream outputStream = (OutputStream) args[0];
            outputStream.write(SOME_BUILT_MULTIPART_BODY);
            return null;
        }).when(httpEntityMock).writeTo(any(OutputStream.class));

        try (MockedStatic<MultipartEntityBuilder> ms = Mockito.mockStatic(MultipartEntityBuilder.class)) {
            ms.when(MultipartEntityBuilder::create).thenReturn(multipartEntityBuilderMock);

            SignedRequest result = signedRequestBuilder.withKeyPair(KEY_PAIR)
                    .withMultipartBoundary(SOME_MULTIPART_BOUNDARY)
                    .withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, SOME_MULTIPART_FILE_NAME)
                    .withBaseUrl(SOME_BASE_URL)
                    .withEndpoint(SOME_ENDPOINT)
                    .withHttpMethod(HttpMethod.GET)
                    .build();

            assertThat(result.getHeaders().get(CONTENT_TYPE), is(SOME_MULTIPART_CONTENT_TYPE.toString()));
            assertThat(result.getData(), is(SOME_BUILT_MULTIPART_BODY));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenThereIsAnErrorBuildingTheMultipartBody() throws Exception {
        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);
        when(multipartEntityBuilderMock.build()).thenReturn(httpEntityMock);

        when(headerElementMock.toString()).thenReturn(SOME_MULTIPART_CONTENT_TYPE.toString());
        when(headerMock.getElements()).thenReturn(new HeaderElement[]{headerElementMock});
        when(httpEntityMock.getContentType()).thenReturn(headerMock);

        IOException ioException = new IOException();
        doThrow(ioException).when(httpEntityMock).writeTo(any(OutputStream.class));

        try (MockedStatic<MultipartEntityBuilder> ms = Mockito.mockStatic(MultipartEntityBuilder.class)) {
            ms.when(MultipartEntityBuilder::create).thenReturn(multipartEntityBuilderMock);

            IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                    signedRequestBuilder.withKeyPair(KEY_PAIR)
                            .withMultipartBoundary(SOME_MULTIPART_BOUNDARY)
                            .withMultipartBinaryBody(
                                    SOME_MULTIPART_BODY_NAME,
                                    SOME_MULTIPART_BODY,
                                    SOME_MULTIPART_CONTENT_TYPE,
                                    SOME_MULTIPART_FILE_NAME
                            )
                            .withBaseUrl(SOME_BASE_URL)
                            .withEndpoint(SOME_ENDPOINT)
                            .withHttpMethod(HttpMethod.GET)
                            .build()
            );

            assertThat(exception.getCause(), is(ioException));
        }
    }

}

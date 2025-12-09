package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;
import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class YotiHttpRequestBuilderTest {

    private static final String SOME_ENDPOINT = "/someEndpoint";
    private static final String SOME_SIGNATURE = "someSignature";
    private static final Map<String, String> SIGNED_REQUEST_HEADERS;
    private static final String SOME_BASE_URL = "someBaseUrl";
    private static final byte[] SOME_BYTES = "someBytes".getBytes();
    private static final String SIGNATURE_PARAMS_STRING = "timestamp=someTimestamp";
    private static final String SOME_MULTIPART_BODY_NAME = "someMultipartBodyName";
    private static final byte[] SOME_MULTIPART_BODY = new byte[]{ 1, 2, 3, 4 };
    private static final ContentType SOME_MULTIPART_CONTENT_TYPE = ContentType.IMAGE_JPEG;
    private static final String SOME_MULTIPART_FILE_NAME = "someMultipartFileName";
    private static final String SOME_MULTIPART_BOUNDARY = "someMultipartBoundary";
    private static final byte[] SOME_BUILT_MULTIPART_BODY = new byte[]{ 5, 6, 7, 8 };

    static {
        Security.addProvider(new BouncyCastleProvider());

        SIGNED_REQUEST_HEADERS = new HashMap<>();
        SIGNED_REQUEST_HEADERS.put(YotiConstants.DIGEST_HEADER, SOME_SIGNATURE);
    }

    @InjectMocks YotiHttpRequestBuilder yotiHttpRequestBuilder;

//    @Mock PathFactory pathFactoryMock;
    @Mock HeadersFactory headersFactoryMock;
    @Mock MultipartEntityBuilder multipartEntityBuilderMock;

    @Mock(answer = Answers.RETURNS_SELF) HttpEntity httpEntityMock;
    @Mock Header authHeaderMock;
    @Mock HeaderElement headerElementMock;
    @Mock AuthStrategy authStrategyMock;

    @Test
    public void withHttpMethod_shouldThrowExceptionWhenSuppliedWithUnsupportedHttpMethod() {
        try {
            yotiHttpRequestBuilder.withHttpMethod("someNonsenseHere");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("someNonsenseHere"));
            return;
        }
        fail("Expected an IllegalArgumentException");

    }

    @Test
    public void build_shouldThrowExceptionWhenMissingAuthStrategy() throws Exception {
        try {
            yotiHttpRequestBuilder.build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("'authStrategy' must not be null"));
            return;
        }
        fail("Expected an IllegalArgumentException");
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingBaseUrl() throws Exception {
        try {
            yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
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
            yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
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
            yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
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
        when(authStrategyMock.createQueryParams()).thenReturn(Arrays.asList(new BasicNameValuePair("timestamp", "someTimestamp")));

        YotiHttpRequest result = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                .withBaseUrl(SOME_BASE_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HTTP_GET)
                .withQueryParameter("someQueryParam", "someParamValue")
                .build();

        assertThat(result.getUri().getQuery(), containsString(SIGNATURE_PARAMS_STRING));
        assertThat(result.getUri().getQuery(), containsString("someQueryParam=someParamValue"));
    }

    @Test
    public void build_shouldCreateSignedRequestWithProvidedHttpHeader() throws Exception {
        YotiHttpRequest result = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                .withBaseUrl(DEFAULT_YOTI_API_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HTTP_GET)
                .withHeader("myCustomHeader", "customHeaderValue")
                .build();

        assertThat(result.getHeaders(), hasEntry("myCustomHeader", "customHeaderValue"));
    }

    @Test
    public void build_shouldIgnoreAnyProvidedAuthenticationHeaders() throws Exception {
        when(authStrategyMock.createQueryParams()).thenReturn(Arrays.asList(new BasicNameValuePair("timestamp", "someTimestamp")));
        when(authStrategyMock.createAuthHeader(HTTP_GET, SOME_ENDPOINT + "?" + SIGNATURE_PARAMS_STRING, null)).thenReturn(authHeaderMock);
        when(headersFactoryMock.create(authHeaderMock)).thenReturn(SIGNED_REQUEST_HEADERS);

        YotiHttpRequest result = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                .withBaseUrl(DEFAULT_YOTI_API_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HTTP_GET)
                .withHeader(DIGEST_HEADER, "customHeaderValue")
                .build();

        verify(authStrategyMock).createAuthHeader(HTTP_GET, SOME_ENDPOINT + "?" + SIGNATURE_PARAMS_STRING, null);
        assertThat(result.getHeaders(), hasEntry(DIGEST_HEADER, SOME_SIGNATURE));
    }

    @Test
    public void withBoundary_shouldThrowWhenBoundaryIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBoundary(null));

        assertThat(ex.getMessage(), containsString("multipartBoundary"));
    }

    @Test
    public void withBoundary_shouldThrowWhenBoundaryIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBoundary(""));

        assertThat(ex.getMessage(), containsString("multipartBoundary"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenNameIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBinaryBody(null, null, null, null));

        assertThat(ex.getMessage(), containsString("name"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenNameIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBinaryBody("", null, null, null));

        assertThat(ex.getMessage(), containsString("name"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenPayloadIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, null, null, null));

        assertThat(ex.getMessage(), containsString("payload"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenContentTypeIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, null, null));

        assertThat(ex.getMessage(), containsString("contentType"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenFileNameIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, null));

        assertThat(ex.getMessage(), containsString("fileName"));
    }

    @Test
    public void withMultipartBinaryBody_shouldThrowWhenFileNameIsEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> yotiHttpRequestBuilder.withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, ""));

        assertThat(ex.getMessage(), containsString("fileName"));
    }

    @Test
    public void shouldRemoveTrailingSlashesFromBaseUrl() throws Exception {
        YotiHttpRequest simpleYotiHttpRequest = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                .withBaseUrl(SOME_BASE_URL + "////////////")
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HTTP_GET)
                .build();

        assertThat(simpleYotiHttpRequest.getUri().toString(), containsString(SOME_BASE_URL + SOME_ENDPOINT));
    }

    @Test
    public void shouldCreateSignedRequestSuccessfullyWithRequiredFieldsOnly() throws Exception {
        when(authStrategyMock.createQueryParams()).thenReturn(Arrays.asList(new BasicNameValuePair("timestamp", "someTimestamp")));
        String fullPath = SOME_ENDPOINT + "?" + SIGNATURE_PARAMS_STRING;
        when(authStrategyMock.createAuthHeader(HTTP_GET, fullPath, null)).thenReturn(authHeaderMock);
        when(headersFactoryMock.create(authHeaderMock)).thenReturn(SIGNED_REQUEST_HEADERS);

        YotiHttpRequest result = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                .withBaseUrl(SOME_BASE_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HTTP_GET)
                .build();

        assertThat(result.getUri().toString(), is(SOME_BASE_URL + fullPath));
        assertThat(result.getMethod(), is(HTTP_GET));
        assertThat(result.getHeaders().get(DIGEST_HEADER), containsString(SOME_SIGNATURE));
        assertThat(result.getHeaders(), hasEntry(DIGEST_HEADER, SOME_SIGNATURE));
        assertNull(result.getData());
    }

    @Test
    public void shouldCreateSignedRequestSuccessfullyWithAllProperties() throws Exception {
        String fullPath = SOME_ENDPOINT + "?someQueryParam=someParamValue&someKey=someValue";
        when(authStrategyMock.createAuthHeader(HTTP_GET, fullPath, SOME_BYTES)).thenReturn(authHeaderMock);
        when(authStrategyMock.createQueryParams()).thenReturn(Arrays.asList(new BasicNameValuePair("someKey","someValue")));
        when(headersFactoryMock.create(authHeaderMock)).thenReturn(SIGNED_REQUEST_HEADERS);

        YotiHttpRequest result = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                .withBaseUrl(SOME_BASE_URL)
                .withEndpoint(SOME_ENDPOINT)
                .withHttpMethod(HTTP_GET)
                .withQueryParameter("someQueryParam", "someParamValue")
                .withHeader("myCustomHeader", "customHeaderValue")
                .withPayload(SOME_BYTES)
                .build();

        assertThat(result.getUri().toString(), is(SOME_BASE_URL + fullPath));
        assertThat(result.getMethod(), is(HTTP_GET));
        assertThat(result.getHeaders().get(DIGEST_HEADER), containsString(SOME_SIGNATURE));
        assertThat(result.getHeaders(), hasEntry(DIGEST_HEADER, SOME_SIGNATURE));
        assertThat(result.getHeaders(), hasEntry("myCustomHeader", "customHeaderValue"));
        assertArrayEquals(SOME_BYTES, result.getData());
    }

    @Test
    public void shouldSetContentTypeToMultipartWhenUserHasSetRequestTypeToMultipart() throws Exception {
//        when(authStrategyMock.createAuthHeader(HTTP_GET, SOME_ENDPOINT + "?" + SIGNATURE_PARAMS_STRING, SOME_BUILT_MULTIPART_BODY)).thenReturn(authHeaderMock);
//        when(headersFactoryMock.create(authHeaderMock)).thenReturn(SIGNED_REQUEST_HEADERS);
        when(multipartEntityBuilderMock.build()).thenReturn(httpEntityMock);
        when(httpEntityMock.getContentType()).thenReturn(new BasicHeader("Content-Type", SOME_MULTIPART_CONTENT_TYPE.toString()));

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

            YotiHttpRequest result = yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                    .withMultipartBoundary(SOME_MULTIPART_BOUNDARY)
                    .withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, SOME_MULTIPART_FILE_NAME)
                    .withBaseUrl(SOME_BASE_URL)
                    .withEndpoint(SOME_ENDPOINT)
                    .withHttpMethod(HTTP_GET)
                    .build();

            assertThat(result.getHeaders().get(CONTENT_TYPE), is(SOME_MULTIPART_CONTENT_TYPE.toString()));
            assertThat(result.getData(), is(SOME_BUILT_MULTIPART_BODY));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenThereIsAnErrorBuildingTheMultipartBody() throws Exception {
//        when(pathFactoryMock.createSignatureParams()).thenReturn(SIGNATURE_PARAMS_STRING);
        when(multipartEntityBuilderMock.build()).thenReturn(httpEntityMock);

        when(headerElementMock.toString()).thenReturn(SOME_MULTIPART_CONTENT_TYPE.toString());
        when(authHeaderMock.getElements()).thenReturn(new HeaderElement[]{headerElementMock});
        when(httpEntityMock.getContentType()).thenReturn(authHeaderMock);

        IOException ioException = new IOException();
        doThrow(ioException).when(httpEntityMock).writeTo(any(OutputStream.class));

        try (MockedStatic<MultipartEntityBuilder> ms = Mockito.mockStatic(MultipartEntityBuilder.class)) {
            ms.when(MultipartEntityBuilder::create).thenReturn(multipartEntityBuilderMock);

            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
                yotiHttpRequestBuilder.withAuthStrategy(authStrategyMock)
                                .withMultipartBoundary(SOME_MULTIPART_BOUNDARY)
                                .withMultipartBinaryBody(SOME_MULTIPART_BODY_NAME, SOME_MULTIPART_BODY, SOME_MULTIPART_CONTENT_TYPE, SOME_MULTIPART_FILE_NAME)
                                .withBaseUrl(SOME_BASE_URL)
                                .withEndpoint(SOME_ENDPOINT)
                                .withHttpMethod(HTTP_GET)
                                .build();
                    });

            assertThat(exception.getCause(), is(ioException));
        }
    }

}

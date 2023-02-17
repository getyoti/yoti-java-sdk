package com.yoti.api.client.spi.remote.http;

import static com.yoti.api.client.spi.remote.util.CryptoUtil.KEY_PAIR_PEM;
import static com.yoti.api.client.spi.remote.util.CryptoUtil.generateKeyPairFrom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.junit.*;

public class RequestBuilderTest {

    private static final String A_SCHEME = "https";
    private static final String A_HOST = "aHost";
    private static final String A_BASE_URL = A_SCHEME + "://" + A_HOST;

    private static final String HEADER_SDK = "X-Yoti-SDK";
    private static final String HEADER_JAVA_VERSION = "Java-Version";
    private static final String HEADER_SDK_VERSION = "X-Yoti-SDK-Version";
    private static final String HEADER_AUTH_DIGEST = "X-Yoti-Auth-Digest";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String A_HEADER_NAME = "aCustomHeader";
    private static final String A_HEADER_VALUE = "aCustomHeaderValue";

    private static final String A_PARAM_NAME = "aCustomParam";
    private static final String A_PARAM_VALUE = "aCustomParamValue";

    private static final String A_MULTIPART_BOUNDARY = "aMultipartBoundary";
    private static final String A_MULTIPART_BODY_NAME = "aMultipartBodyName";
    private static final byte[] A_MULTIPART_BODY = new byte[] { 1, 2, 3 };
    private static final String A_MULTIPART_FILE_NAME = "aMultipartFileName";
    private static final ContentType A_MULTIPART_CONTENT_TYPE = ContentType.IMAGE_JPEG;

    private static KeyPair KEY_PAIR;

    @BeforeClass
    public static void setUpClass() throws Exception {
        KEY_PAIR = generateKeyPairFrom(KEY_PAIR_PEM);
    }

    @Test
    public void build_shouldThrowExceptionWhenNullEndpoint() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> RequestBuilder.forEndpoint(null).build()
        );

        assertThat(ex.getMessage(), containsString("endpoint"));
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingEndpointParams() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_PARAMS).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, reqBuilder::build);

        assertThat(ex.getMessage(), containsString("Unexpected number of arguments"));
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingBaseUrl() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, reqBuilder::build);

        assertThat(ex.getMessage(), containsString("baseUrl"));
    }

    @Test
    public void build_shouldThrowExceptionWhenMissingKeyPair() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, reqBuilder::build);

        assertThat(ex.getMessage(), containsString("keyPair"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithNullBoundary() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBoundary(null)
        );

        assertThat(ex.getMessage(), containsString("multipart boundary"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithEmptyBoundary() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBoundary("")
        );

        assertThat(ex.getMessage(), containsString("multipart boundary"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithNullMultipartBinaryBodyName() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBinaryBody(
                        null,
                        A_MULTIPART_BODY,
                        A_MULTIPART_CONTENT_TYPE,
                        A_MULTIPART_FILE_NAME
                )
        );

        assertThat(ex.getMessage(), containsString("name"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithEmptyMultipartBinaryBodyName() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBinaryBody(
                        "",
                        A_MULTIPART_BODY,
                        A_MULTIPART_CONTENT_TYPE,
                        A_MULTIPART_FILE_NAME
                )
        );

        assertThat(ex.getMessage(), containsString("name"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithNullMultipartBinaryBodyPayload() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBinaryBody(
                        A_MULTIPART_BODY_NAME,
                        null,
                        A_MULTIPART_CONTENT_TYPE,
                        A_MULTIPART_FILE_NAME
                )
        );

        assertThat(ex.getMessage(), containsString("payload"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithNullMultipartBinaryBodyContentType() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBinaryBody(
                        A_MULTIPART_BODY_NAME,
                        A_MULTIPART_BODY,
                        null,
                        A_MULTIPART_FILE_NAME
                )
        );

        assertThat(ex.getMessage(), containsString("contentType"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithNullMultipartBinaryBodyFileName() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBinaryBody(
                        A_MULTIPART_BODY_NAME,
                        A_MULTIPART_BODY,
                        A_MULTIPART_CONTENT_TYPE,
                        null
                )
        );

        assertThat(ex.getMessage(), containsString("fileName"));
    }

    @Test
    public void withMultipartBoundary_shouldThrowExceptionWithEmptyMultipartBinaryBodyFileName() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY).withBaseUrl(A_BASE_URL);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reqBuilder.withMultipartBinaryBody(
                        A_MULTIPART_BODY_NAME,
                        A_MULTIPART_BODY,
                        A_MULTIPART_CONTENT_TYPE,
                        ""
                )
        );

        assertThat(ex.getMessage(), containsString("fileName"));
    }

    @Test
    public void build_shouldCreateUnSignedRequestWithDefaultHttpMethod() {
        byte[] body = "aBody".getBytes(StandardCharsets.UTF_8);

        Request unsignedRequest = null;
        try {
            unsignedRequest = RequestBuilder.forEndpoint(Endpoint.NOT_SIGNED_PATH)
                    .withBaseUrl(A_BASE_URL)
                    .withHeader(A_HEADER_NAME, A_HEADER_VALUE)
                    .withQueryParameter(A_PARAM_NAME, A_PARAM_VALUE)
                    .withPayload(body)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(unsignedRequest.method().name(), equalTo(HttpMethod.GET.name()));

        URI uri = unsignedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.NOT_SIGNED_PATH.path()));
        assertThat(uri.getQuery(), stringContainsInOrder(A_PARAM_NAME, A_PARAM_VALUE));

        Map<String, String> headers = unsignedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), nullValue());
        assertThat(headers.get(HEADER_CONTENT_TYPE), equalTo(ContentType.APPLICATION_JSON.getMimeType()));
        assertThat(headers.get(A_HEADER_NAME), equalTo(A_HEADER_VALUE));

        assertThat(unsignedRequest.data(), equalTo(body));
    }

    @Test
    public void build_shouldCreateUnSignedRequestRemovingTrailingSlashesFromBaseUrl() {
        RequestBuilder.Builder reqBuilder = RequestBuilder.forEndpoint(Endpoint.NOT_SIGNED_PATH)
                .withBaseUrl(A_BASE_URL + "////////////");

        Request unsignedRequest = null;
        try {
            unsignedRequest = reqBuilder.build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        URI uri = unsignedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.NOT_SIGNED_PATH.path()));
    }

    @Test
    public void build_shouldCreateUnSignedRequestWithEndpointDefinedHttpMethod() {
        Request unsignedRequest = null;
        try {
            unsignedRequest = RequestBuilder.forEndpoint(Endpoint.NOT_SIGNED_METHOD_PATH)
                    .withBaseUrl(A_BASE_URL)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(unsignedRequest.method().name(), equalTo(HttpMethod.POST.name()));

        URI uri = unsignedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.NOT_SIGNED_METHOD_PATH.path()));
        assertThat(uri.getQuery(), nullValue());

        Map<String, String> headers = unsignedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), nullValue());
    }

    @Test
    public void build_shouldCreateSignedRequestWithGetAsDefaultHttpMethod() {
        byte[] body = "aBody".getBytes(StandardCharsets.UTF_8);

        Request signedRequest = null;
        try {
            signedRequest = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY)
                    .withBaseUrl(A_BASE_URL)
                    .withKeyPair(KEY_PAIR)
                    .withHeader(A_HEADER_NAME, A_HEADER_VALUE)
                    .withQueryParameter(A_PARAM_NAME, A_PARAM_VALUE)
                    .withPayload(body)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(signedRequest.method().name(), equalTo(HttpMethod.GET.name()));

        URI uri = signedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.PATH_ONLY.path()));
        assertThat(uri.getQuery(), stringContainsInOrder(A_PARAM_NAME, A_PARAM_VALUE, "nonce", "timestamp"));

        Map<String, String> headers = signedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), notNullValue());
        assertThat(headers.get(HEADER_CONTENT_TYPE), equalTo(ContentType.APPLICATION_JSON.getMimeType()));
        assertThat(headers.get(A_HEADER_NAME), equalTo(A_HEADER_VALUE));

        assertThat(signedRequest.data(), equalTo(body));
    }

    @Test
    public void build_shouldCreateSignedRequestWithEndpointDefinedHttpMethod() {
        Request signedRequest = null;
        try {
            signedRequest = RequestBuilder.forEndpoint(Endpoint.METHOD_PATH)
                    .withBaseUrl(A_BASE_URL)
                    .withKeyPair(KEY_PAIR)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(signedRequest.method().name(), equalTo(HttpMethod.PUT.name()));

        URI uri = signedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.METHOD_PATH.path()));
        assertThat(uri.getQuery(), stringContainsInOrder("nonce", "timestamp"));

        Map<String, String> headers = signedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), notNullValue());
    }

    @Test
    public void build_shouldCreateRequestWithEndpointPathParam() {
        String param = "aParamValue";

        Request signedRequest = null;
        try {
            signedRequest = RequestBuilder.forEndpoint(Endpoint.PATH_PARAMS, param)
                    .withBaseUrl(A_BASE_URL)
                    .withKeyPair(KEY_PAIR)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(signedRequest.method().name(), equalTo(HttpMethod.GET.name()));

        URI uri = signedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.PATH_PARAMS.path(param)));
        assertThat(uri.getQuery(), stringContainsInOrder("nonce", "timestamp"));

        Map<String, String> headers = signedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), notNullValue());

        assertThat(signedRequest.data(), nullValue());
    }

    @Test
    public void build_shouldCreateRequestWithEndpointQueryParam() {
        String param = "aParamValue";

        Request signedRequest = null;
        try {
            signedRequest = RequestBuilder.forEndpoint(Endpoint.PATH_QUERY_STRING, param)
                    .withBaseUrl(A_BASE_URL)
                    .withKeyPair(KEY_PAIR)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(signedRequest.method().name(), equalTo(HttpMethod.GET.name()));

        URI uri = signedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));

        String endpointPath = Endpoint.PATH_QUERY_STRING.path(param);
        assertThat(uri.getPath(), equalTo(endpointPath.substring(0, endpointPath.indexOf("?"))));
        assertThat(uri.getQuery(), stringContainsInOrder(param, "nonce", "timestamp"));

        Map<String, String> headers = signedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), notNullValue());

        assertThat(signedRequest.data(), nullValue());
    }

    @Test
    public void build_shouldCreateRequestWithEndpointPathAndQueryParam() {
        String pathParam = "aPathParamValue";
        String queryParam = "aQueryParamValue";

        Request signedRequest = null;
        try {
            signedRequest = RequestBuilder.forEndpoint(Endpoint.PATH_PARAM_QUERY_STRING, pathParam, queryParam)
                    .withBaseUrl(A_BASE_URL)
                    .withKeyPair(KEY_PAIR)
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(signedRequest.method().name(), equalTo(HttpMethod.GET.name()));

        URI uri = signedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));

        String endpointPath = Endpoint.PATH_PARAM_QUERY_STRING.path(pathParam, queryParam);
        assertThat(uri.getPath(), equalTo(endpointPath.substring(0, endpointPath.indexOf("?"))));
        assertThat(uri.getQuery(), stringContainsInOrder(queryParam, "nonce", "timestamp"));

        Map<String, String> headers = signedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), notNullValue());

        assertThat(signedRequest.data(), nullValue());
    }

    @Test
    public void build_shouldCreateRequestWithMultipart() {
        Request signedRequest = null;
        try {
            signedRequest = RequestBuilder.forEndpoint(Endpoint.PATH_ONLY)
                    .withBaseUrl(A_BASE_URL)
                    .withKeyPair(KEY_PAIR)
                    .withMultipartBoundary(A_MULTIPART_BOUNDARY)
                    .withMultipartBinaryBody(
                            A_HEADER_NAME,
                            A_MULTIPART_BODY,
                            A_MULTIPART_CONTENT_TYPE,
                            A_MULTIPART_FILE_NAME
                    )
                    .build();
        } catch (Exception ex) {
            fail("No exceptions expected");
        }

        assertThat(signedRequest.method().name(), equalTo(HttpMethod.GET.name()));

        URI uri = signedRequest.uri();
        assertThat(uri.getScheme(), equalTo(A_SCHEME));
        assertThat(uri.getHost(), equalTo(A_HOST));
        assertThat(uri.getPath(), equalTo(Endpoint.PATH_ONLY.path()));
        assertThat(uri.getQuery(), stringContainsInOrder("nonce", "timestamp"));

        Map<String, String> headers = signedRequest.headers();
        assertThat(headers.get(HEADER_SDK), notNullValue());
        assertThat(headers.get(HEADER_SDK_VERSION), notNullValue());
        assertThat(headers.get(HEADER_JAVA_VERSION), notNullValue());
        assertThat(headers.get(HEADER_AUTH_DIGEST), notNullValue());
        assertThat(headers.get(HEADER_CONTENT_TYPE),
                stringContainsInOrder(ContentType.MULTIPART_FORM_DATA.getMimeType(), "boundary")
        );

        assertThat(signedRequest.data(), notNullValue());
    }

}

package com.yoti.api.client.spi.remote.http.fetcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.http.Request;
import com.yoti.api.client.spi.remote.http.ResourceException;
import com.yoti.api.client.spi.remote.http.Response;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageResourceFetcherTest {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_PNG = "image/png";
    private static final String CONTENT_TYPE_WEBP = "image/webp";
    private static final String CONTENT_TYPE_JPEG = "image/jpeg";

    private static final byte[] SOME_RESPONSE_BODY = new byte[] { 5, 6, 7, 8 };

    @InjectMocks ImageResourceFetcher resourceFetcher;

    @Mock RawResourceFetcher rawResourceFetcher;
    @Mock Request request;

    @Test
    public void doRequest_shouldReturnPngImage() throws Exception {
        Map<String, List<String>> headersMap = buildResponseHeaders(
                CONTENT_TYPE,
                Collections.singletonList(CONTENT_TYPE_PNG)
        );
        
        Response Response = new Response(HttpURLConnection.HTTP_OK, SOME_RESPONSE_BODY, headersMap);
        when(rawResourceFetcher.doRequest(request)).thenReturn(Response);

        Image result = resourceFetcher.doRequest(request);

        assertThat(result.getMimeType(), is(CONTENT_TYPE_PNG));
        assertArrayEquals(result.getContent(), SOME_RESPONSE_BODY);
    }

    @Test
    public void doRequest_shouldReturnJpegImage() throws Exception {
        Map<String, List<String>> headersMap = buildResponseHeaders(
                CONTENT_TYPE,
                Collections.singletonList(CONTENT_TYPE_JPEG)
        );
        
        Response Response = new Response(HttpURLConnection.HTTP_OK, SOME_RESPONSE_BODY, headersMap);
        when(rawResourceFetcher.doRequest(request)).thenReturn(Response);

        Image result = resourceFetcher.doRequest(request);

        String mimeType = result.getMimeType();
        byte[] content = result.getContent();

        assertThat(mimeType, is(CONTENT_TYPE_JPEG));
        assertArrayEquals(content, SOME_RESPONSE_BODY);
    }

    @Test(expected = ResourceException.class)
    public void doRequest_shouldThrowResourceExceptionForUnsupportedImageType() throws Exception {
        Map<String, List<String>> headersMap = buildResponseHeaders(
                CONTENT_TYPE,
                Collections.singletonList(CONTENT_TYPE_WEBP)
        );
        
        Response Response = new Response(HttpURLConnection.HTTP_OK, SOME_RESPONSE_BODY, headersMap);
        when(rawResourceFetcher.doRequest(request)).thenReturn(Response);

        resourceFetcher.doRequest(request);
    }

    private static Map<String, List<String>> buildResponseHeaders(String name, List<String> values) {
        HashMap<String, List<String>> result = new HashMap<>();
        result.put(name, values);
        return result;
    }

}

package com.yoti.api.client.spi.remote.call;

import static java.util.Arrays.asList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageResourceFetcherTest {

    private static final int SOME_OK_STATUS_CODE = 200;
    private static final byte[] SOME_RESPONSE_BODY = new byte[] { 5, 6, 7, 8 };

    @InjectMocks ImageResourceFetcher testObj;

    @Mock RawResourceFetcher rawResourceFetcher;
    @Mock SignedRequest signedRequest;

    private Map<String, List<String>> createResponseHeaderMap(String name, List<String> values) {
        HashMap<String, List<String>> result = new HashMap<>();
        result.put(name, values);
        return result;
    }

    @Test
    public void doRequest_shouldReturnPngImage() throws Exception {
        Map<String, List<String>> headersMap = createResponseHeaderMap("Content-Type", asList(YotiConstants.CONTENT_TYPE_PNG));
        Response response = new Response(SOME_OK_STATUS_CODE, SOME_RESPONSE_BODY, headersMap);
        when(rawResourceFetcher.doRequest(signedRequest)).thenReturn(response);

        Image result = testObj.doRequest(signedRequest);

        assertThat(result.getMimeType(), is(YotiConstants.CONTENT_TYPE_PNG));
        assertTrue(Arrays.equals(result.getContent(), SOME_RESPONSE_BODY));
    }

    @Test
    public void doRequest_shouldReturnJpegImage() throws Exception {
        Map<String, List<String>> headersMap = createResponseHeaderMap("Content-Type", asList(YotiConstants.CONTENT_TYPE_JPEG));
        Response response = new Response(SOME_OK_STATUS_CODE, SOME_RESPONSE_BODY, headersMap);
        when(rawResourceFetcher.doRequest(signedRequest)).thenReturn(response);

        Image result = testObj.doRequest(signedRequest);

        String mimeType = result.getMimeType();
        byte[] content = result.getContent();

        assertThat(mimeType, is(YotiConstants.CONTENT_TYPE_JPEG));
        assertTrue(Arrays.equals(content, SOME_RESPONSE_BODY));
    }

    @Test(expected = ResourceException.class)
    public void doRequest_shouldThrowResourceExceptionForUnsupportedImageType() throws Exception {
        Map<String, List<String>> headersMap = createResponseHeaderMap("Content-Type", asList("image/webp"));
        Response response = new Response(SOME_OK_STATUS_CODE, SOME_RESPONSE_BODY, headersMap);
        when(rawResourceFetcher.doRequest(signedRequest)).thenReturn(response);

        testObj.doRequest(signedRequest);
    }

}

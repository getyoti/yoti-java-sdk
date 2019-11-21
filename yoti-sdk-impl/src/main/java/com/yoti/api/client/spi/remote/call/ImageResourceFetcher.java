package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JPEG;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_PNG;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.JpegAttributeValue;
import com.yoti.api.client.spi.remote.PngAttributeValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ImageResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(ImageResourceFetcher.class);

    private final RawResourceFetcher rawResourceFetcher;

    public static ImageResourceFetcher newInstance(RawResourceFetcher rawResourceFetcher) {
        return new ImageResourceFetcher(rawResourceFetcher);
    }

    private ImageResourceFetcher(RawResourceFetcher rawResourceFetcher) {
        this.rawResourceFetcher = rawResourceFetcher;
    }

    Image doRequest(SignedRequest signedRequest) throws IOException, ResourceException {
        SignedRequestResponse signedRequestResponse = rawResourceFetcher.doRequest(signedRequest);
        String contentType = getContentType(signedRequestResponse.getResponseHeaders());
        byte[] responseBody = signedRequestResponse.getResponseBody();
        switch (contentType) {
            case CONTENT_TYPE_PNG:
                return new PngAttributeValue(responseBody);
            case CONTENT_TYPE_JPEG:
                return new JpegAttributeValue(responseBody);
            default:
                LOG.error("Failed to convert image of type: (" + contentType + ")");
                throw new ResourceException(signedRequestResponse.getResponseCode(), "Failed to convert image of type: (" + contentType + ")", null);
        }
    }

    private String getContentType(Map<String, List<String>> responseHeaders) {
        List<String> values = responseHeaders.get(CONTENT_TYPE);
        if (values != null && values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

}

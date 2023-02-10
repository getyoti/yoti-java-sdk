package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JPEG;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_PNG;

import java.io.IOException;
import java.util.Optional;

import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.JpegAttributeValue;
import com.yoti.api.client.spi.remote.PngAttributeValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ImageResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(ImageResourceFetcher.class);

    private final RawResourceFetcher rawResourceFetcher;

    static ImageResourceFetcher newInstance(RawResourceFetcher rawResourceFetcher) {
        return new ImageResourceFetcher(rawResourceFetcher);
    }

    private ImageResourceFetcher(RawResourceFetcher rawResourceFetcher) {
        this.rawResourceFetcher = rawResourceFetcher;
    }

    Image doRequest(SignedRequest signedRequest) throws IOException, ResourceException {
        Response response = rawResourceFetcher.doRequest(signedRequest);
        String contentType = Optional.ofNullable(response.headers().get(CONTENT_TYPE))
                .map(values -> values.get(0))
                .orElseThrow(() -> new ResourceException(response.code(), "No Content Type found in response"));

        switch (contentType) {
            case CONTENT_TYPE_PNG:
                return new PngAttributeValue(response.body());
            case CONTENT_TYPE_JPEG:
                return new JpegAttributeValue(response.body());
            default:
                LOG.error("Failed to convert image of type: (" + contentType + ")");
                throw new ResourceException(response.code(), "Failed to convert image of type: (" + contentType + ")", null);
        }
    }

}

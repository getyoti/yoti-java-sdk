package com.yoti.api.client.spi.remote.http.fetcher;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JPEG;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_PNG;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.JpegAttributeValue;
import com.yoti.api.client.spi.remote.PngAttributeValue;
import com.yoti.api.client.spi.remote.http.Request;
import com.yoti.api.client.spi.remote.http.ResourceException;
import com.yoti.api.client.spi.remote.http.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageResourceFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(ImageResourceFetcher.class);

    private final RawResourceFetcher rawResourceFetcher;

    public static ImageResourceFetcher newInstance() {
        return new ImageResourceFetcher(RawResourceFetcher.newInstance());
    }

    private ImageResourceFetcher(RawResourceFetcher rawResourceFetcher) {
        this.rawResourceFetcher = rawResourceFetcher;
    }

    public Image doRequest(Request request) throws IOException, ResourceException {
        Response response = rawResourceFetcher.doRequest(request);
        String contentType = getContentType(response.headers());

        switch (Objects.requireNonNull(contentType)) {
            case CONTENT_TYPE_PNG:
                return new PngAttributeValue(response.body());
            case CONTENT_TYPE_JPEG:
                return new JpegAttributeValue(response.body());
            default:
                LOG.error("Failed to convert image of type: (" + contentType + ")");
                throw new ResourceException(response.code(), "Failed to convert image of type: (" + contentType + ")", null);
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

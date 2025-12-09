package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;

public class YotiHttpRequestBuilderFactory {

    private static final PathFactory pathFactory;
    private static final HeadersFactory headersFactory;
    private static final JsonResourceFetcher jsonResourceFetcher;
    private static final RawResourceFetcher rawResourceFetcher;
    private static final ImageResourceFetcher imageResourceFetcher;

    static {
        pathFactory = new PathFactory();
        headersFactory = new HeadersFactory();
        rawResourceFetcher = new RawResourceFetcher();
        jsonResourceFetcher = JsonResourceFetcher.newInstance(rawResourceFetcher);
        imageResourceFetcher = ImageResourceFetcher.newInstance(rawResourceFetcher);
    }

    public YotiHttpRequestBuilder create() {
        return new YotiHttpRequestBuilder(pathFactory, headersFactory, jsonResourceFetcher, rawResourceFetcher, imageResourceFetcher);
    }

}

package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;

public class YotiHttpRequestBuilderFactory {

    private static final HeadersFactory headersFactory;
    private static final JsonResourceFetcher jsonResourceFetcher;
    private static final RawResourceFetcher rawResourceFetcher;
    private static final ImageResourceFetcher imageResourceFetcher;

    static {
        headersFactory = new HeadersFactory();
        rawResourceFetcher = new RawResourceFetcher();
        jsonResourceFetcher = JsonResourceFetcher.newInstance(rawResourceFetcher);
        imageResourceFetcher = ImageResourceFetcher.newInstance(rawResourceFetcher);
    }

    public YotiHttpRequestBuilder create() {
        return new YotiHttpRequestBuilder(headersFactory, jsonResourceFetcher, rawResourceFetcher, imageResourceFetcher);
    }

}

package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

public class SignedRequestBuilderFactory {

    private static final PathFactory pathFactory;
    private static final SignedMessageFactory signedMessageFactory;
    private static final HeadersFactory headersFactory;
    private static final JsonResourceFetcher jsonResourceFetcher;
    private static final RawResourceFetcher rawResourceFetcher;
    private static final ImageResourceFetcher imageResourceFetcher;

    static {
        pathFactory = new PathFactory();
        signedMessageFactory = SignedMessageFactory.newInstance();
        headersFactory = new HeadersFactory();
        rawResourceFetcher = new RawResourceFetcher();
        jsonResourceFetcher = JsonResourceFetcher.newInstance(rawResourceFetcher);
        imageResourceFetcher = ImageResourceFetcher.newInstance(rawResourceFetcher);
    }

    public SignedRequestBuilder create() {
        return new SignedRequestBuilder(pathFactory, signedMessageFactory, headersFactory, jsonResourceFetcher, rawResourceFetcher, imageResourceFetcher);
    }

}

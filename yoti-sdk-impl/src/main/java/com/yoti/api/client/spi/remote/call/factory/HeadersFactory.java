package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_KEY_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

import java.util.HashMap;
import java.util.Map;

public class HeadersFactory {

    public Map<String, String> create(String digest) {
        Map<String, String> headers = new HashMap<>();
        headers.put(DIGEST_HEADER, digest);
        headers.put(YOTI_SDK_HEADER, JAVA);
        headers.put(YOTI_SDK_VERSION_HEADER, SDK_VERSION);
        headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);
        return headers;
    }

    @Deprecated
    public Map<String, String> create(String digest, String authKey) {
        Map<String, String> headers = create(digest);
        headers.put(AUTH_KEY_HEADER, authKey);
        return headers;
    }

}

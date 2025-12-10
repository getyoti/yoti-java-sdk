package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

public class HeadersFactory {

    public Map<String, String> create(List<Header> authHeaders) {
        Map<String, String> headers = new HashMap<>();
        for (Header authHeader : authHeaders) {
            headers.put(authHeader.getName(), authHeader.getValue());
        }
        headers.put(YOTI_SDK_HEADER, JAVA);
        headers.put(YOTI_SDK_VERSION_HEADER, SDK_VERSION);
        return headers;
    }

}

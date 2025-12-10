package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AmlSignedRequestStrategy extends SignedRequestStrategy {

    private final String sdkId;

    public AmlSignedRequestStrategy(KeyPair keyPair, String sdkId) {
        super(keyPair);
        this.sdkId = sdkId;
    }

    @Override
    public List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException {
        return Collections.singletonList(createDigestHeader(httpMethod, endpoint, payload));
    }

    @Override
    public List<NameValuePair> createQueryParams() {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("appId", sdkId));
        queryParams.addAll(createSignedRequestParams());
        return queryParams;
    }

}

package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Collections;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;

public class SimpleSignedRequestStrategy extends SignedRequestStrategy {

    public SimpleSignedRequestStrategy(KeyPair keyPair) {
        super(keyPair);
    }

    @Override
    public List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException {
        return Collections.singletonList(createDigestHeader(httpMethod, endpoint, payload));
    }

    @Override
    public List<NameValuePair> createQueryParams() {
        return createSignedRequestParams();
    }

}

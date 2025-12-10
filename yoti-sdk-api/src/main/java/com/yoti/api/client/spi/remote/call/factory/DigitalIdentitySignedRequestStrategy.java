package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_ID_HEADER;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

public class DigitalIdentitySignedRequestStrategy extends SignedRequestStrategy {

    private final String sdkId;

    public DigitalIdentitySignedRequestStrategy(KeyPair keyPair, String sdkId) {
        super(keyPair);
        this.sdkId = sdkId;
    }

    @Override
    public List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException {
        return Arrays.asList(createDigestHeader(httpMethod, endpoint, payload), new BasicHeader(AUTH_ID_HEADER, sdkId));
    }

    @Override
    public List<NameValuePair> createQueryParams() {
        return createSignedRequestParams();
    }

}

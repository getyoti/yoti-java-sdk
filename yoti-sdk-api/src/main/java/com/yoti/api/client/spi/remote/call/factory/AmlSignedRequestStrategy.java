package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.System.nanoTime;
import static java.util.UUID.randomUUID;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.spi.remote.call.YotiConstants;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class AmlSignedRequestStrategy implements AuthStrategy {

    private static final SignedMessageFactory signedMessageFactory;

    static {
        signedMessageFactory = SignedMessageFactory.newInstance();
    }

    private final KeyPair keyPair;
    private final String sdkId;

    public AmlSignedRequestStrategy(KeyPair keyPair, String sdkId) {
        this.keyPair = keyPair;
        this.sdkId = sdkId;
    }

    @Override
    public List<Header> createAuthHeaders(String httpMethod, String endpoint, byte[] payload) throws GeneralSecurityException {
        String digest;
        if (payload == null) {
            digest = signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint);
        } else {
            digest = signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint, payload);
        }
        return Collections.singletonList(new BasicHeader(YotiConstants.DIGEST_HEADER, digest));
    }

    @Override
    public List<NameValuePair> createQueryParams() {
        List<NameValuePair> queryParams = new ArrayList<>();
        queryParams.add(new BasicNameValuePair("appId", sdkId));
        queryParams.add(new BasicNameValuePair("nonce", randomUUID().toString()));
        queryParams.add(new BasicNameValuePair("timestamp", Long.toString(nanoTime() / 1000)));
        return queryParams;
    }

}

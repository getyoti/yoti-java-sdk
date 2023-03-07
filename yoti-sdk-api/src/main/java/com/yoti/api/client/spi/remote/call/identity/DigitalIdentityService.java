package com.yoti.api.client.spi.remote.call.identity;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_ID_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_IDENTITY_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.validation.Validation.notNull;
import static com.yoti.validation.Validation.notNullOrEmpty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;
import com.yoti.json.ResourceMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitalIdentityService {

    private static final Logger LOG = LoggerFactory.getLogger(DigitalIdentityService.class);

    private final UnsignedPathFactory pathFactory;
    private final SignedRequestBuilderFactory requestBuilderFactory;

    private final String apiUrl;

    public DigitalIdentityService(UnsignedPathFactory pathFactory, SignedRequestBuilderFactory requestBuilderFactory) {
        this.pathFactory = pathFactory;
        this.requestBuilderFactory = requestBuilderFactory;

        this.apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_IDENTITY_URL);
    }

    public static DigitalIdentityService newInstance() {
        return new DigitalIdentityService(new UnsignedPathFactory(), new SignedRequestBuilderFactory());
    }

    public ShareSession createShareSession(String sdkId, KeyPair keyPair, ShareSessionRequest shareSessionRequest)
            throws DigitalIdentityException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");
        notNull(shareSessionRequest, "Share Session request");

        LOG.debug("Requesting Share Session Creation for SDK ID '{}", sdkId);

        String path = pathFactory.createIdentitySessionPath();

        try {
            byte[] payload = ResourceMapper.writeValueAsString(shareSessionRequest);
            SignedRequest request = createSignedRequest(sdkId, keyPair, path, payload);

            return request.execute(ShareSession.class);
        } catch (IOException ex) {
            throw new DigitalIdentityException("Error parsing the request: ", ex);
        } catch (URISyntaxException ex) {
            throw new DigitalIdentityException("Error building the request: ", ex);
        } catch (GeneralSecurityException ex) {
            throw new DigitalIdentityException("Error signing the request: ", ex);
        } catch (ResourceException ex) {
            throw new DigitalIdentityException("Error posting the request: ", ex);
        }
    }

    SignedRequest createSignedRequest(String appId, KeyPair keyPair, String path, byte[] payload)
            throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        return requestBuilderFactory.create()
                .withKeyPair(keyPair)
                .withBaseUrl(apiUrl)
                .withEndpoint(path)
                .withHeader(AUTH_ID_HEADER, appId)
                .withHttpMethod(HTTP_POST)
                .withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .withPayload(payload)
                .build();
    }

    public Object fetchShareSession(String sessionId) {
        return null;
    }

    public Object createShareQrCode(String sessionId) {
        return null;
    }

    public Object fetchShareQrCode(String qrCodeId) {
        return null;
    }

    public Object fetchShareReceipt(String receiptId) {
        return null;
    }

}

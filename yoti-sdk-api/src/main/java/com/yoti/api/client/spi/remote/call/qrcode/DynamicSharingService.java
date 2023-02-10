package com.yoti.api.client.spi.remote.call.qrcode;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.shareurl.DynamicScenario;
import com.yoti.api.client.shareurl.DynamicShareException;
import com.yoti.api.client.shareurl.ShareUrlResult;
import com.yoti.api.client.spi.remote.call.HttpMethod;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DynamicSharingService {

    public static DynamicSharingService newInstance() {
        return new DynamicSharingService(
                new UnsignedPathFactory(),
                new ObjectMapper(),
                new SignedRequestBuilderFactory()
        );
    }

    private static final Logger LOG = LoggerFactory.getLogger(DynamicSharingService.class);

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final SignedRequestBuilderFactory signedRequestBuilderFactory;

    private final String apiUrl;

    DynamicSharingService(UnsignedPathFactory unsignedPathFactory,
            ObjectMapper objectMapper,
            SignedRequestBuilderFactory signedRequestBuilderFactory) {
        this.unsignedPathFactory = unsignedPathFactory;
        this.objectMapper = objectMapper;
        this.signedRequestBuilderFactory = signedRequestBuilderFactory;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public ShareUrlResult createShareUrl(String appId, KeyPair keyPair, DynamicScenario dynamicScenario) throws DynamicShareException {
        notNull(appId, "Application id");
        notNull(keyPair, "Application key Pair");
        notNull(dynamicScenario, "Dynamic scenario");

        String path = unsignedPathFactory.createDynamicSharingPath(appId);
        LOG.info("Requesting Dynamic QR Code at {}", path);

        try {
            byte[] body = objectMapper.writeValueAsString(dynamicScenario).getBytes(DEFAULT_CHARSET);

            SignedRequest signedRequest = createSignedRequest(keyPair, path, body);

            return signedRequest.execute(ShareUrlResult.class);
        } catch (ResourceException ex) {
            throw new DynamicShareException("Error posting the request: ", ex);
        } catch (IOException ex) {
            throw new DynamicShareException("Error building the request: ", ex);
        }
    }

    SignedRequest createSignedRequest(KeyPair keyPair, String path, byte[] body) throws DynamicShareException {
        try {
            return signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withPayload(body)
                    .withHttpMethod(HttpMethod.POST)
                    .build();
        } catch (GeneralSecurityException ex) {
            throw new DynamicShareException("Error signing the request: ", ex);
        } catch (UnsupportedEncodingException | URISyntaxException ex) {
            throw new DynamicShareException("Error building the request: ", ex);
        }
    }

}

package com.yoti.api.client.spi.remote.call.qrcode;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.shareurl.DynamicScenario;
import com.yoti.api.client.shareurl.DynamicShareException;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DynamicSharingService {

    public static DynamicSharingService newInstance() {
        return new DynamicSharingService(
                new UnsignedPathFactory(),
                new ObjectMapper(),
                SignedRequestBuilder.newInstance()
        );
    }

    private static final Logger LOG = LoggerFactory.getLogger(DynamicSharingService.class);

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final SignedRequestBuilder signedRequestBuilder;

    private final String apiUrl;

    DynamicSharingService(UnsignedPathFactory unsignedPathFactory,
            ObjectMapper objectMapper,
            SignedRequestBuilder signedRequestBuilder) {
        this.unsignedPathFactory = unsignedPathFactory;
        this.objectMapper = objectMapper;
        this.signedRequestBuilder = signedRequestBuilder;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public SimpleShareUrlResult createShareUrl(String appId, KeyPair keyPair, DynamicScenario dynamicScenario) throws DynamicShareException {
        notNull(appId, "Application id");
        notNull(keyPair, "Application key Pair");
        notNull(dynamicScenario, "Dynamic scenario");

        String path = unsignedPathFactory.createDynamicSharingPath(appId);
        LOG.info("Requesting Dynamic QR Code at {}", path);

        try {
            byte[] body = objectMapper.writeValueAsString(dynamicScenario).getBytes(DEFAULT_CHARSET);

            SignedRequest signedRequest = this.signedRequestBuilder
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withPayload(body)
                    .withHttpMethod("POST")
                    .build();

            return signedRequest.execute(SimpleShareUrlResult.class);
        } catch (GeneralSecurityException ex) {
            throw new DynamicShareException("Error signing the request: ", ex);
        } catch (ResourceException ex) {
            throw new DynamicShareException("Error posting the request: ", ex);
        } catch (IOException ex) {
            throw new DynamicShareException("Error building the request: ", ex);
        } catch (Exception ex) {
            throw new DynamicShareException("Error initiating the share: ", ex);
        }
    }

}

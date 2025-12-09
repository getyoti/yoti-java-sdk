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
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.SimpleSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DynamicSharingService {

    public static DynamicSharingService newInstance(KeyPair keyPair) {
        return new DynamicSharingService(
                new UnsignedPathFactory(),
                new ObjectMapper(),
                new YotiHttpRequestBuilderFactory(),
                new SimpleSignedRequestStrategy(keyPair)
        );
    }

    private static final Logger LOG = LoggerFactory.getLogger(DynamicSharingService.class);

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory;
    private final SimpleSignedRequestStrategy simpleSignedRequestStrategy;

    private final String apiUrl;

    private DynamicSharingService(UnsignedPathFactory unsignedPathFactory,
            ObjectMapper objectMapper,
            YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory,
            SimpleSignedRequestStrategy simpleSignedRequestStrategy) {
        this.unsignedPathFactory = unsignedPathFactory;
        this.objectMapper = objectMapper;
        this.yotiHttpRequestBuilderFactory = yotiHttpRequestBuilderFactory;
        this.simpleSignedRequestStrategy = simpleSignedRequestStrategy;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public ShareUrlResult createShareUrl(String appId, DynamicScenario dynamicScenario) throws DynamicShareException {
        notNull(appId, "Application id");
        notNull(dynamicScenario, "Dynamic scenario");

        String path = unsignedPathFactory.createDynamicSharingPath(appId);
        LOG.info("Requesting Dynamic QR Code at {}", path);

        try {
            byte[] body = objectMapper.writeValueAsString(dynamicScenario).getBytes(DEFAULT_CHARSET);

            YotiHttpRequest yotiHttpRequest = createSignedRequest(path, body);

            return yotiHttpRequest.execute(ShareUrlResult.class);
        } catch (ResourceException ex) {
            throw new DynamicShareException("Error posting the request: ", ex);
        } catch (IOException ex) {
            throw new DynamicShareException("Error building the request: ", ex);
        }
    }

    YotiHttpRequest createSignedRequest(String path, byte[] body) throws DynamicShareException {
        try {
            return yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(simpleSignedRequestStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withPayload(body)
                    .withHttpMethod("POST")
                    .build();
        } catch (GeneralSecurityException ex) {
            throw new DynamicShareException("Error signing the request: ", ex);
        } catch (UnsupportedEncodingException | URISyntaxException ex) {
            throw new DynamicShareException("Error building the request: ", ex);
        }
    }

}

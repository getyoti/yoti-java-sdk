package com.yoti.api.client.spi.remote.call.qrcode;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Map;

import com.yoti.api.client.qrcode.DynamicScenario;
import com.yoti.api.client.qrcode.DynamicShareException;
import com.yoti.api.client.spi.remote.call.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicSharingService {

    public static DynamicSharingService newInstance() {
        return new DynamicSharingService(
                new PathFactory(),
                new HeadersFactory(),
                new ObjectMapper(),
                JsonResourceFetcher.newInstance(),
                SignedMessageFactory.newInstance());
    }

    private static final Logger LOG = LoggerFactory.getLogger(DynamicSharingService.class);

    private final PathFactory pathFactory;
    private final HeadersFactory headersFactory;
    private final ObjectMapper objectMapper;
    private final ResourceFetcher resourceFetcher;
    private final SignedMessageFactory signedMessageFactory;

    private final String apiUrl;

    public DynamicSharingService(PathFactory pathFactory,
            HeadersFactory headersFactory,
            ObjectMapper objectMapper,
            ResourceFetcher resourceFetcher,
            SignedMessageFactory signedMessageFactory) {
        this.pathFactory = pathFactory;
        this.headersFactory = headersFactory;
        this.objectMapper = objectMapper;
        this.resourceFetcher = resourceFetcher;
        this.signedMessageFactory = signedMessageFactory;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public SimpleShareUrlResult createShareUrl(String appId, KeyPair keyPair, DynamicScenario dynamicScenario) throws DynamicShareException {
        notNull(appId, "Application id");
        notNull(keyPair, "Application key Pair");
        notNull(dynamicScenario, "Dynamic scenario");

        String path = pathFactory.createDynamicSharingPath(appId);
        LOG.info("Requesting Dynamic QR Code at {}", path);

        try {
            byte[] body = objectMapper.writeValueAsString(dynamicScenario).getBytes(DEFAULT_CHARSET);
            String digest = signedMessageFactory.create(keyPair.getPrivate(), HTTP_POST, path, body);
            Map<String, String> headers = headersFactory.create(digest);
            UrlConnector urlConnector = UrlConnector.get(apiUrl + path);
            return resourceFetcher.postResource(urlConnector, body, headers, SimpleShareUrlResult.class);

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

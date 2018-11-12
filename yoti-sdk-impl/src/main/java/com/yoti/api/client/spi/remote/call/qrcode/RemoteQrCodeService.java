package com.yoti.api.client.spi.remote.call.qrcode;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import static org.bouncycastle.cms.CMSAttributeTableGenerator.CONTENT_TYPE;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import com.yoti.api.client.qrcode.DynamicScenario;
import com.yoti.api.client.qrcode.QRCodeException;
import com.yoti.api.client.spi.remote.call.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.call.QrCodeService;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteQrCodeService implements QrCodeService {

    public static RemoteQrCodeService newInstance() {
        return new RemoteQrCodeService(
                new PathFactory(),
                new ObjectMapper(),
                JsonResourceFetcher.newInstance(),
                SignedMessageFactory.newInstance());
    }

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    private static final Logger LOG = LoggerFactory.getLogger(RemoteQrCodeService.class);

    private final PathFactory pathFactory;
    private final ObjectMapper objectMapper;
    private final ResourceFetcher resourceFetcher;
    private final SignedMessageFactory signedMessageFactory;

    private final String apiUrl;

    public RemoteQrCodeService(PathFactory pathFactory,
            ObjectMapper objectMapper,
            ResourceFetcher resourceFetcher,
            SignedMessageFactory signedMessageFactory) {
        this.pathFactory = pathFactory;
        this.objectMapper = objectMapper;
        this.resourceFetcher = resourceFetcher;
        this.signedMessageFactory = signedMessageFactory;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    @Override
    public SimpleQrCode requestQRCode(String appId, KeyPair keyPair, DynamicScenario dynamicScenario) throws QRCodeException {
        notNull(appId, "Application id");
        notNull(keyPair, "Application key Pair");
        notNull(dynamicScenario, "Dynamic scenario");

        String path = pathFactory.createQrCodePath(appId);
        LOG.info("Requesting Dynamic QR Code at {}", path);

        try {
            byte[] body = objectMapper.writeValueAsString(dynamicScenario).getBytes(DEFAULT_CHARSET);
            String digest = signedMessageFactory.create(keyPair.getPrivate(), HTTP_POST, path, body);

            Map<String, String> headers = new HashMap();
            headers.put(DIGEST_HEADER, digest);
            headers.put(YOTI_SDK_HEADER, JAVA);
            headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);

            UrlConnector urlConnector = UrlConnector.get(apiUrl + path);
            return resourceFetcher.postResource(urlConnector, body, headers, SimpleQrCode.class);

        } catch (GeneralSecurityException ex) {
            throw new QRCodeException("Error signing the request: ", ex);
        } catch (ResourceException ex) {
            throw new QRCodeException("Error posting the request: ", ex);
        } catch (IOException ex) {
            throw new QRCodeException("Error building the request: ", ex);
        } catch (Exception ex) {
            throw new QRCodeException("Error requesting the Dynamic QRCode: ", ex);
        }
    }

}

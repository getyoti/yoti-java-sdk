package com.yoti.api.client.spi.remote.call.identity;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
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
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Optional;

import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionQrCode;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilder;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;
import com.yoti.json.ResourceMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitalIdentityService {

    private static final Logger LOG = LoggerFactory.getLogger(DigitalIdentityService.class);

    private static final byte[] EMPTY_JSON = "{}".getBytes(StandardCharsets.UTF_8);

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

        String path = pathFactory.createIdentitySessionPath();

        LOG.debug("Requesting Share Session Creation for SDK ID '{}' at '{}'", sdkId, path);

        try {
            byte[] payload = ResourceMapper.writeValueAsString(shareSessionRequest);
            SignedRequest request = createSignedRequest(sdkId, keyPair, path, HTTP_POST, payload);

            return request.execute(ShareSession.class);
        } catch (IOException ex) {
            throw new DigitalIdentityException("Error while parsing the share session creation request ", ex);
        } catch (URISyntaxException ex) {
            throw new DigitalIdentityException("Error while building the share session creation request ", ex);
        } catch (GeneralSecurityException ex) {
            throw new DigitalIdentityException("Error while signing the share session creation request ", ex);
        } catch (ResourceException ex) {
            throw new DigitalIdentityException("Error while executing the share session creation request ", ex);
        }
    }

    public Object fetchShareSession(String sessionId) {
        return null;
    }

    public ShareSessionQrCode createShareQrCode(String sdkId, KeyPair keyPair, String sessionId)
            throws DigitalIdentityException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");
        notNullOrEmpty(sessionId, "Session ID");

        String path = pathFactory.createIdentitySessionQrCodePath(sessionId);

        LOG.debug("Requesting Share Session QR code Creation for session ID '{}' at '{}'", sessionId, path);

        try {
            SignedRequest request = createSignedRequest(sdkId, keyPair, path, HTTP_POST, EMPTY_JSON);

            return request.execute(ShareSessionQrCode.class);
        } catch (GeneralSecurityException ex) {
            throw new DigitalIdentityException("Error while signing the share QR code creation request ", ex);
        } catch (IOException | URISyntaxException | ResourceException ex) {
            throw new DigitalIdentityException("Error while executing the share QR code creation request ", ex);
        }
    }

    public ShareSessionQrCode fetchShareQrCode(String sdkId, KeyPair keyPair, String qrCodeId)
            throws DigitalIdentityException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");
        notNullOrEmpty(qrCodeId, "QR Code ID");

        String path = pathFactory.createIdentitySessionQrCodeRetrievalPath(qrCodeId);

        LOG.info("Requesting Share Session QR code with ID '{} at '{}'", qrCodeId, path);

        try {
            SignedRequest request = createSignedRequest(sdkId, keyPair, path);

            return request.execute(ShareSessionQrCode.class);
        } catch (GeneralSecurityException ex) {
            throw new DigitalIdentityException("Error while signing the share QR code fetch request ", ex);
        } catch (IOException | URISyntaxException | ResourceException ex) {
            throw new DigitalIdentityException("Error while executing the share QR code fetch request ", ex);
        }
    }

    public Object fetchShareReceipt(String receiptId) {
        return null;
    }

    SignedRequest createSignedRequest(String sdkId, KeyPair keyPair, String path)
            throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        return createSignedRequest(sdkId, keyPair, path, HTTP_GET, null);
    }

    SignedRequest createSignedRequest(String sdkId, KeyPair keyPair, String path, String method, byte[] payload)
            throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        SignedRequestBuilder requestBuilder = requestBuilderFactory.create()
                .withKeyPair(keyPair)
                .withBaseUrl(apiUrl)
                .withEndpoint(path)
                .withHeader(AUTH_ID_HEADER, sdkId)
                .withHttpMethod(method);

        Optional.ofNullable(payload).map(v ->
                requestBuilder.withPayload(v).withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
        );

        return requestBuilder.build();
    }

}

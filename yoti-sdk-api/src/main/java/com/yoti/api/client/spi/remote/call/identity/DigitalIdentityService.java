package com.yoti.api.client.spi.remote.call.identity;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_IDENTITY_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.Optional;

import com.yoti.api.client.identity.MatchRequest;
import com.yoti.api.client.identity.MatchResult;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionQrCode;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilder;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.factory.DigitalIdentitySignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;
import com.yoti.json.ResourceMapper;
import com.yoti.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigitalIdentityService {

    private static final Logger LOG = LoggerFactory.getLogger(DigitalIdentityService.class);

    private static final byte[] EMPTY_JSON = "{}".getBytes(StandardCharsets.UTF_8);

    private final UnsignedPathFactory pathFactory;
    private final YotiHttpRequestBuilderFactory requestBuilderFactory;
    private final ReceiptParser receiptParser;

    private final String apiUrl;

    public DigitalIdentityService(
            UnsignedPathFactory pathFactory,
            YotiHttpRequestBuilderFactory requestBuilderFactory,
            ReceiptParser receiptParser) {
        this.pathFactory = pathFactory;
        this.requestBuilderFactory = requestBuilderFactory;
        this.receiptParser = receiptParser;

        this.apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_IDENTITY_URL);
    }

    public static DigitalIdentityService newInstance() {
        return new DigitalIdentityService(
                new UnsignedPathFactory(),
                new YotiHttpRequestBuilderFactory(),
                ReceiptParser.newInstance()
        );
    }

    public ShareSession createShareSession(DigitalIdentitySignedRequestStrategy authStrategy, ShareSessionRequest shareSessionRequest)
            throws DigitalIdentityException {
        Validation.notNull(authStrategy, "signedRequestStrategy");
        Validation.notNull(shareSessionRequest, "Share Session request");

        String path = pathFactory.createIdentitySessionPath();

        try {
            byte[] payload = ResourceMapper.writeValueAsString(shareSessionRequest);
            return createSignedRequest(authStrategy, path, HTTP_POST, payload).execute(ShareSession.class);
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

    public ShareSession fetchShareSession(DigitalIdentitySignedRequestStrategy authStrategy, String sessionId)
            throws DigitalIdentityException {
        Validation.notNull(authStrategy, "signedRequestStrategy");
        Validation.notNull(sessionId, "Session ID");

        String path = pathFactory.createIdentitySessionRetrievalPath(sessionId);

        LOG.debug("Requesting share session '{}' at '{}'", sessionId, path);

        try {
            return createSignedRequest(authStrategy, path).execute(ShareSession.class);
        } catch (Exception ex) {
            throw new DigitalIdentityException(
                    String.format("Error while fetching the share session '{%s}' ", sessionId),
                    ex
            );
        }
    }

    public ShareSessionQrCode createShareQrCode(DigitalIdentitySignedRequestStrategy authStrategy, String sessionId)
            throws DigitalIdentityException {
        Validation.notNull(authStrategy, "signedRequestStrategy");
        Validation.notNullOrEmpty(sessionId, "Session ID");

        String path = pathFactory.createIdentitySessionQrCodePath(sessionId);

        LOG.debug("Requesting share session '{}' QR code creation at '{}'", sessionId, path);

        try {
            return createSignedRequest(authStrategy, path, HTTP_POST, EMPTY_JSON).execute(ShareSessionQrCode.class);
        } catch (GeneralSecurityException ex) {
            throw new DigitalIdentityException("Error while signing the share QR code creation request ", ex);
        } catch (IOException | URISyntaxException | ResourceException ex) {
            throw new DigitalIdentityException("Error while executing the share QR code creation request ", ex);
        }
    }

    public ShareSessionQrCode fetchShareQrCode(DigitalIdentitySignedRequestStrategy authStrategy, String qrCodeId)
            throws DigitalIdentityException {
        Validation.notNull(authStrategy, "signedRequestStrategy");
        Validation.notNullOrEmpty(qrCodeId, "QR Code ID");

        String path = pathFactory.createIdentitySessionQrCodeRetrievalPath(qrCodeId);

        LOG.debug("Requesting share session QR code '{} at '{}'", qrCodeId, path);

        try {
            return createSignedRequest(authStrategy, path).execute(ShareSessionQrCode.class);
        } catch (Exception ex) {
            throw new DigitalIdentityException(
                    String.format("Error while fetching the share session QR code '{%s}' ", qrCodeId),
                    ex
            );
        }
    }

    public Receipt fetchShareReceipt(DigitalIdentitySignedRequestStrategy authStrategy, KeyPair keyPair, String receiptId) throws DigitalIdentityException {
        WrappedReceipt wrappedReceipt = doFetchShareReceipt(authStrategy, receiptId);

        return Optional.ofNullable(wrappedReceipt.getError())
                .map(ignored -> receiptParser.create(wrappedReceipt))
                .orElseGet(() -> {
                    ReceiptItemKey receiptKey = fetchShareReceiptKey(authStrategy, wrappedReceipt);

                    return receiptParser.create(wrappedReceipt, receiptKey, keyPair.getPrivate());
                });
    }

    private WrappedReceipt doFetchShareReceipt(DigitalIdentitySignedRequestStrategy authStrategy, String receiptId) {
        String path = pathFactory.createIdentitySessionReceiptRetrievalPath(receiptId);

        LOG.debug("Requesting share session receipt '{}' at '{}'", receiptId, path);

        try {
            return createSignedRequest(authStrategy, path).execute(WrappedReceipt.class);
        } catch (Exception ex) {
            throw new DigitalIdentityException(
                    String.format("Error while fetching the share session QR code '{%s}' ", receiptId),
                    ex
            );
        }
    }

    private ReceiptItemKey fetchShareReceiptKey(DigitalIdentitySignedRequestStrategy authStrategy, WrappedReceipt wrappedReceipt)
            throws DigitalIdentityException {
        String wrappedItemKeyId = wrappedReceipt.getWrappedItemKeyId();

        String path = pathFactory.createIdentitySessionReceiptKeyRetrievalPath(wrappedItemKeyId);

        LOG.debug("Requesting share session receipt item key '{}' at '{}'", wrappedItemKeyId, path);

        try {
            return createSignedRequest(authStrategy, path).execute(ReceiptItemKey.class);
        } catch (Exception ex) {
            throw new DigitalIdentityException(
                    String.format("Error while fetching the share session receipt key '{%s}' ", wrappedItemKeyId),
                    ex
            );
        }
    }

    public MatchResult fetchMatch(DigitalIdentitySignedRequestStrategy authStrategy, MatchRequest matchRequest)
            throws DigitalIdentityException {
        Validation.notNull(authStrategy, "Application Key Pair");
        Validation.notNull(matchRequest, "DID Match request");

        String path = pathFactory.createIdentityMatchPath();

        try {
            byte[] payload = ResourceMapper.writeValueAsString(matchRequest);
            return createSignedRequest(authStrategy, path, HTTP_POST, payload).execute(MatchResult.class);
        } catch (IOException ex) {
            throw new DigitalIdentityException("Error while parsing the DID Match request", ex);
        } catch (URISyntaxException ex) {
            throw new DigitalIdentityException("Error while building the DID Match request", ex);
        } catch (GeneralSecurityException ex) {
            throw new DigitalIdentityException("Error while signing the DID Match request", ex);
        } catch (ResourceException ex) {
            throw new DigitalIdentityException("Error while executing the DID Match request", ex);
        }
    }

    YotiHttpRequest createSignedRequest(DigitalIdentitySignedRequestStrategy authStrategy, String path)
            throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        return createSignedRequest(authStrategy, path, HTTP_GET, null);
    }

    YotiHttpRequest createSignedRequest(DigitalIdentitySignedRequestStrategy authStrategy, String path, String method, byte[] payload)
            throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        YotiHttpRequestBuilder request = requestBuilderFactory.create()
                .withAuthStrategy(authStrategy)
                .withBaseUrl(apiUrl)
                .withEndpoint(path)
                .withHttpMethod(method);

        return Optional.ofNullable(payload)
                .map(request::withPayload)
                .map(r -> r.withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON))
                .orElse(request)
                .build();
    }

}

package com.yoti.api.client.spi.remote.call;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.Base64;

public final class RemoteProfileService implements ProfileService {
    public static final String YOTI_API_PATH_PREFIX = "/api/v1";

    private static final Logger LOG = LoggerFactory.getLogger(RemoteProfileService.class);

    private static final String PROPERTY_YOTI_API_URL = "yoti.api.url";
    private static final String DEFAULT_YOTI_HOST = "https://api.yoti.com";
    public static final String DEFAULT_YOTI_API_URL = DEFAULT_YOTI_HOST + YOTI_API_PATH_PREFIX;
    private static final String MESSAGE_PREFIX = "GET&";

    private static final String PATH_TEMPLATE = "/profile/{}?nonce={}&timestamp={}&appId={}";
    private static final String PARAM_PLACEHOLDER = "\\{\\}";

    private static final String AUTH_KEY_HEADER = "X-Yoti-Auth-Key";
    private static final String DIGEST_HEADER = "X-Yoti-Auth-Digest";
    private static final String YOTI_SDK_HEADER = "X-Yoti-SDK";
    private static final String CONTENT_TYPE = "Content-Type";

    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String STRING_ENCODING = "UTF-8";

    private final ResourceFetcher resourceFetcher;
    private final String apiUrl;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static RemoteProfileService newInstance() {
        return new RemoteProfileService(new JsonResourceFetcher());
    }

    RemoteProfileService(ResourceFetcher resourceFetcher) {
        this.resourceFetcher = resourceFetcher;
        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    @Override
    public Receipt getReceipt(KeyPair keyPair, String appId, String connectToken) throws ProfileException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(connectToken, "Connect token");

        String path = createRequestPath(appId, connectToken);
        String digest = base64(signMessage(assembleMessage(path), keyPair.getPrivate()));
        String authKey = base64(keyPair.getPublic().getEncoded());

        Receipt receipt;
        try {
            receipt = fetchReceipt(path, digest, authKey);
        } catch (IOException ioe) {
            throw new ProfileException("Error calling service to get profile", ioe);
        }
        return receipt;
    }

    private String createRequestPath(String appId, String connectToken) {
        String template = PATH_TEMPLATE.replaceFirst(PARAM_PLACEHOLDER, connectToken);
        template = template.replaceFirst(PARAM_PLACEHOLDER, UUID.randomUUID().toString());
        template = template.replaceFirst(PARAM_PLACEHOLDER, "" + System.nanoTime() / 1000);
        return template.replaceFirst(PARAM_PLACEHOLDER, appId);
    }

    private byte[] assembleMessage(String path) {
        try {
            return (MESSAGE_PREFIX + path).getBytes(STRING_ENCODING);
        } catch (UnsupportedEncodingException e) {
            // should not happen. JVM spec mandates UTF-8 support.
            throw new RuntimeException("Unable to convert string to bytes", e);
        }
    }

    private byte[] signMessage(byte[] message, PrivateKey privateKey) throws ProfileException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
            signature.initSign(privateKey, new SecureRandom());
            signature.update(message);
            return signature.sign();
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Cannot sign request", gse);
        }
    }

    private Receipt fetchReceipt(String resourcePath, String digest, String authKey)
            throws IOException, ProfileException {
        LOG.info("Fetching profile from resource at {}", resourcePath);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTH_KEY_HEADER, authKey);
        headers.put(DIGEST_HEADER, digest);
        headers.put(YOTI_SDK_HEADER, "Java");
        headers.put(CONTENT_TYPE, "application/json");

        UrlConnector urlConnector = UrlConnector.get(apiUrl + resourcePath);

        try {
            ProfileResponse response = resourceFetcher.fetchResource(urlConnector, headers, ProfileResponse.class);
            return response.getReceipt();
        } catch (ResourceException re) {
            int responseCode = re.getResponseCode();
            switch (responseCode) {
            case HTTP_INTERNAL_ERROR:
                throw new ProfileException("Error completing sharing: " + re.getResponseBody(), re);
            case HTTP_NOT_FOUND:
                throw new ProfileException("Profile not found. This can be due to a used or expired token. Details: " 
                    + re.getResponseBody(), re);
            default:
                throw new ProfileException("Unexpected response: " + responseCode + " " + re.getResponseBody(), re);
            }
        }
    }

    private static String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private <T> T notNull(T value, String item) {
        if (value == null) {
            throw new IllegalArgumentException(item + " must not be null.");
        }
        return value;
    }

}

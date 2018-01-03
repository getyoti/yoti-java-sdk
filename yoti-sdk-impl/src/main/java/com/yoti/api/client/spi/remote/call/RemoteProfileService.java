package com.yoti.api.client.spi.remote.call;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignatureFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import static com.yoti.api.client.spi.remote.Base64.base64;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_KEY_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.bouncycastle.cms.CMSAttributeTableGenerator.CONTENT_TYPE;

public final class RemoteProfileService implements ProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteProfileService.class);

    private final ResourceFetcher resourceFetcher;
    private final PathFactory pathFactory;
    private final SignatureFactory signatureFactory;
    private final String apiUrl;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static RemoteProfileService newInstance() {
        return new RemoteProfileService(JsonResourceFetcher.newInstance(), new PathFactory(), SignatureFactory.newInstance());
    }

    RemoteProfileService(ResourceFetcher resourceFetcher,
                         PathFactory profilePathFactory,
                         SignatureFactory signatureFactory) {
        this.resourceFetcher = resourceFetcher;
        this.pathFactory = profilePathFactory;
        this.signatureFactory = signatureFactory;
        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    @Override
    public Receipt getReceipt(KeyPair keyPair, String appId, String connectToken) throws ProfileException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(connectToken, "Connect token");

        String path = pathFactory.createProfilePath(appId, connectToken);

        try {
            String digest = signatureFactory.create(keyPair.getPrivate(), HTTP_GET, path);
            String authKey = base64(keyPair.getPublic().getEncoded());
            return fetchReceipt(path, digest, authKey);
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Cannot sign request", gse);
        } catch (IOException ioe) {
            throw new ProfileException("Error calling service to get profile", ioe);
        }
    }

    private Receipt fetchReceipt(String resourcePath, String digest, String authKey) throws IOException, ProfileException {
        LOG.info("Fetching profile from resource at {}", resourcePath);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(AUTH_KEY_HEADER, authKey);
        headers.put(DIGEST_HEADER, digest);
        headers.put(YOTI_SDK_HEADER, JAVA);
        headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);

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

}

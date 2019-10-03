package com.yoti.api.client.spi.remote.call;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import static com.yoti.api.client.spi.remote.Base64.base64;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.AUTH_KEY_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RemoteProfileService implements ProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteProfileService.class);

    private final UnsignedPathFactory unsignedPathFactory;
    private final SignedRequestBuilder signedRequestBuilder;
    private final String apiUrl;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static RemoteProfileService newInstance() {
        return new RemoteProfileService(
                new UnsignedPathFactory(),
                SignedRequestBuilder.newInstance()
        );
    }

    RemoteProfileService(UnsignedPathFactory profilePathFactory,
            SignedRequestBuilder signedRequestBuilder) {
        this.unsignedPathFactory = profilePathFactory;
        this.signedRequestBuilder = signedRequestBuilder;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    @Override
    public Receipt getReceipt(KeyPair keyPair, String appId, String connectToken) throws ProfileException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(connectToken, "Connect token");

        String path = unsignedPathFactory.createProfilePath(appId, connectToken);

        try {
            String authKey = base64(keyPair.getPublic().getEncoded());

            SignedRequest signedRequest = this.signedRequestBuilder
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .withHeader(AUTH_KEY_HEADER, authKey)
                    .build();
            return fetchReceipt(signedRequest);
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Cannot sign request", gse);
        } catch (URISyntaxException uriSyntaxException) {
            throw new ProfileException("Error creating request", uriSyntaxException);
        } catch (IOException ioe) {
            throw new ProfileException("Error calling service to get profile", ioe);
        }
    }

    private Receipt fetchReceipt(SignedRequest signedRequest) throws IOException, ProfileException {
        LOG.info("Fetching profile from resource at '{}'", signedRequest.getUri());
        try {
            ProfileResponse response = signedRequest.execute(ProfileResponse.class);
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

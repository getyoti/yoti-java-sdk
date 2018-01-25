package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.spi.remote.call.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignatureFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_API_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class RemoteAmlService {

    private final PathFactory pathFactory;
    private final ObjectMapper objectMapper;
    private final SignatureFactory signatureFactory;
    private final ResourceFetcher resourceFetcher;
    private final String apiUrl;

    public static RemoteAmlService newInstance() {
        return new RemoteAmlService(JsonResourceFetcher.newInstance(), new PathFactory(), new ObjectMapper(), SignatureFactory.newInstance());
    }

    public RemoteAmlService(ResourceFetcher resourceFetcher,
                            PathFactory pathFactory,
                            ObjectMapper objectMapper,
                            SignatureFactory signatureFactory) {
        this.pathFactory = pathFactory;
        this.objectMapper = objectMapper;
        this.signatureFactory = signatureFactory;
        this.resourceFetcher = resourceFetcher;

        apiUrl = System.getProperty(PROPERTY_YOTI_API_URL, DEFAULT_YOTI_API_URL);
    }

    public SimpleAmlResult performCheck(KeyPair keyPair, String appId, AmlProfile amlProfile) throws AmlException {
        notNull(keyPair, "Key pair");
        notNull(appId, "Application id");
        notNull(amlProfile, "amlProfile");

        try {
            String resourcePath = pathFactory.createAmlPath(appId);
            byte[] body = objectMapper.writeValueAsString(amlProfile).getBytes(DEFAULT_CHARSET);
            String digest = signatureFactory.create(keyPair.getPrivate(), HTTP_POST, resourcePath, body);

            Map<String, String> headers = new HashMap<String, String>();
            headers.put(DIGEST_HEADER, digest);
            headers.put(YOTI_SDK_HEADER, JAVA);
            headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);

            UrlConnector urlConnector = UrlConnector.get(apiUrl + resourcePath);

            return resourceFetcher.postResource(urlConnector, body, headers, SimpleAmlResult.class);
        } catch (IOException ioException) {
            throw new AmlException("Error communicating with AML endpoint", ioException);
        } catch (GeneralSecurityException generalSecurityException) {
            throw new AmlException("Cannot sign request", generalSecurityException);
        } catch (ResourceException resourceException) {
            throw createExceptionFromStatusCode(resourceException);
        }
    }

    private AmlException createExceptionFromStatusCode(ResourceException e) throws AmlException {
        switch (e.getResponseCode()) {
            case HTTP_BAD_REQUEST:
                return new AmlException("Failed validation:\n" + e.getResponseBody(), e);
            case HTTP_UNAUTHORIZED:
                return new AmlException("Failed authorization with the given key:\n" + e.getResponseBody(), e);
            case HTTP_INTERNAL_ERROR:
                return new AmlException("An unexpected error occured on the server:\n" + e.getResponseBody(), e);
            default:
                return new AmlException("Unexpected error:\n" + e.getResponseBody(), e);
        }
    }

}

package com.yoti.api.client.docs;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_DELETE;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.docs.support.SupportedDocumentsResponse;
import com.yoti.api.client.spi.remote.MediaValue;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.SignedRequestResponse;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service built to handle the interactions between the client and Doc Scan APIs
 */
final class DocScanService {

    private static final Logger LOG = LoggerFactory.getLogger(DocScanService.class);
    private static final int HTTP_STATUS_NO_CONTENT = 204;

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final SignedRequestBuilderFactory signedRequestBuilderFactory;
    private final String apiUrl;

    private DocScanService(UnsignedPathFactory pathFactory,
            ObjectMapper objectMapper,
            SignedRequestBuilderFactory signedRequestBuilderFactory) {
        this.unsignedPathFactory = pathFactory;
        this.objectMapper = objectMapper;
        this.signedRequestBuilderFactory = signedRequestBuilderFactory;

        apiUrl = System.getProperty(PROPERTY_YOTI_DOCS_URL, DEFAULT_YOTI_DOCS_URL);
    }

    public static DocScanService newInstance() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return new DocScanService(new UnsignedPathFactory(), objectMapper, new SignedRequestBuilderFactory());
    }

    /**
     * Uses the supplied session specification to create a session
     *
     * @param sdkId       the SDK ID
     * @param keyPair     the {@code KeyPair}
     * @param sessionSpec the {@code SessionSpec}
     * @return the session creation result
     * @throws DocScanException if there was an error
     */
    public CreateSessionResult createSession(String sdkId, KeyPair keyPair, SessionSpec sessionSpec) throws DocScanException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application key Pair");
        notNull(sessionSpec, "sessionSpec");

        String path = unsignedPathFactory.createNewYotiDocsSessionPath(sdkId);
        LOG.info("Creating session at '{}'", path);

        try {
            byte[] payload = objectMapper.writeValueAsBytes(sessionSpec);

            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_POST)
                    .withPayload(payload)
                    .withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .build();

            return signedRequest.execute(CreateSessionResult.class);
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error posting the request: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new DocScanException("Error creating the session: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the current state of a given session
     *
     * @param sdkId     the SDK ID
     * @param keyPair   the {@code KeyPair}
     * @param sessionId the session ID
     * @return the session state
     * @throws DocScanException if there was an error
     */
    public GetSessionResult retrieveSession(String sdkId, KeyPair keyPair, String sessionId) throws DocScanException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application key Pair");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createYotiDocsSessionPath(sdkId, sessionId);
        LOG.info("Fetching session from '{}'", path);

        try {
            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return signedRequest.execute(GetSessionResult.class);
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new DocScanException("Error retrieving the session: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a session and all of its associated content
     *
     * @param sdkId     the SDK ID
     * @param keyPair   the {@code KeyPair}
     * @param sessionId the session ID
     * @throws DocScanException if there was an error
     */
    public void deleteSession(String sdkId, KeyPair keyPair, String sessionId) throws DocScanException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application key Pair");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createYotiDocsSessionPath(sdkId, sessionId);
        LOG.info("Deleting session from '{}'", path);

        try {
            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_DELETE)
                    .build();

            signedRequest.execute();
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the DELETE: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new DocScanException("Error deleting the session: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves {@link Media} content for a given session and media ID
     *
     * @param sdkId     the SDK ID
     * @param keyPair   the {@code KeyPair}
     * @param sessionId the session ID
     * @param mediaId   the media ID
     * @return the {@code Media} content, null if 204 No Content
     * @throws DocScanException if there was an error
     */
    public Media getMediaContent(String sdkId, KeyPair keyPair, String sessionId, String mediaId) throws DocScanException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application key Pair");
        notNullOrEmpty(sessionId, "sessionId");
        notNullOrEmpty(mediaId, "mediaId");

        String path = unsignedPathFactory.createMediaContentPath(sdkId, sessionId, mediaId);
        LOG.info("Fetching media from '{}'", path);

        try {
            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();
            SignedRequestResponse response = signedRequest.execute();

            if (response.getResponseCode() == HTTP_STATUS_NO_CONTENT) {
                return null;
            }
            return new MediaValue(findContentType(response), response.getResponseBody());
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    /**
     * Deletes media content for a given session and media ID
     *
     * @param sdkId     the SDK ID
     * @param keyPair   the {@code KeyPair}
     * @param sessionId the session ID
     * @param mediaId   the media ID
     * @throws DocScanException if there was an error
     */
    public void deleteMediaContent(String sdkId, KeyPair keyPair, String sessionId, String mediaId) throws DocScanException {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application key Pair");
        notNullOrEmpty(sessionId, "sessionId");
        notNullOrEmpty(mediaId, "mediaId");

        String path = unsignedPathFactory.createMediaContentPath(sdkId, sessionId, mediaId);
        LOG.info("Deleting media at '{}'", path);

        try {
            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_DELETE)
                    .build();

            signedRequest.execute();
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the DELETE: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public SupportedDocumentsResponse getSupportedDocuments(KeyPair keyPair) throws DocScanException {
        notNull(keyPair, "Application key Pair");

        String path = unsignedPathFactory.createGetSupportedDocumentsPath();

        try {
            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withKeyPair(keyPair)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return signedRequest.execute(SupportedDocumentsResponse.class);
        } catch (GeneralSecurityException | ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    private String findContentType(SignedRequestResponse response) {
        List<String> contentTypeValues = null;
        for (Map.Entry<String, List<String>> entry : response.getResponseHeaders().entrySet()) {
            if (entry.getKey() != null && entry.getKey().toLowerCase(Locale.ENGLISH).equals(CONTENT_TYPE.toLowerCase(Locale.ENGLISH))) {
                contentTypeValues = entry.getValue();
                break;
            }
        }
        return contentTypeValues == null || contentTypeValues.isEmpty() ? "" : contentTypeValues.get(0);
    }

}

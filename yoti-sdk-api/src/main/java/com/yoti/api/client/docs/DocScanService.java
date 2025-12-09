package com.yoti.api.client.docs;

import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_DELETE;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_GET;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_POST;
import static com.yoti.api.client.spi.remote.call.HttpMethod.HTTP_PUT;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.create.facecapture.CreateFaceCaptureResourcePayload;
import com.yoti.api.client.docs.session.create.facecapture.UploadFaceCaptureImagePayload;
import com.yoti.api.client.docs.session.devicemetadata.MetadataResponse;
import com.yoti.api.client.docs.session.instructions.Instructions;
import com.yoti.api.client.docs.session.retrieve.CreateFaceCaptureResourceResponse;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.docs.session.retrieve.configuration.SessionConfigurationResponse;
import com.yoti.api.client.docs.session.retrieve.instructions.ContactProfileResponse;
import com.yoti.api.client.docs.session.retrieve.instructions.InstructionsResponse;
import com.yoti.api.client.docs.support.SupportedDocumentsResponse;
import com.yoti.api.client.spi.remote.MediaValue;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.YotiHttpRequest;
import com.yoti.api.client.spi.remote.call.YotiHttpRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.YotiHttpResponse;
import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;
import com.yoti.api.client.spi.remote.call.factory.UnsignedPathFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service built to handle the interactions between the client and Doc Scan APIs
 */
final class DocScanService {

    private static final Logger LOG = LoggerFactory.getLogger(DocScanService.class);

    private static final TypeReference<List<MetadataResponse>> METADATA_RESPONSE_TYPE_REF = new TypeReference<List<MetadataResponse>>() {};

    private static final int HTTP_STATUS_NO_CONTENT = 204;
    private static final String YOTI_MULTIPART_BOUNDARY = "yoti-doc-scan-boundary";

    private final UnsignedPathFactory unsignedPathFactory;
    private final ObjectMapper objectMapper;
    private final YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory;
    private final String apiUrl;

    private DocScanService(UnsignedPathFactory pathFactory,
            ObjectMapper objectMapper,
            YotiHttpRequestBuilderFactory yotiHttpRequestBuilderFactory) {
        this.unsignedPathFactory = pathFactory;
        this.objectMapper = objectMapper;
        this.yotiHttpRequestBuilderFactory = yotiHttpRequestBuilderFactory;

        apiUrl = System.getProperty(PROPERTY_YOTI_DOCS_URL, DEFAULT_YOTI_DOCS_URL);
    }

    public static DocScanService newInstance() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());

        return new DocScanService(new UnsignedPathFactory(), objectMapper, new YotiHttpRequestBuilderFactory());
    }

    /**
     * Uses the supplied session specification to create a session
     *
     * @param authStrategy the {@code AuthStrategy}
     * @param sessionSpec  the {@code SessionSpec}
     * @return the session creation result
     * @throws DocScanException if there was an error
     */
    public CreateSessionResult createSession(AuthStrategy authStrategy, SessionSpec sessionSpec) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNull(sessionSpec, "sessionSpec");

        String path = unsignedPathFactory.createNewYotiDocsSessionPath();
        LOG.info("Creating session at '{}'", path);

        try {
            byte[] payload = objectMapper.writeValueAsBytes(sessionSpec);

            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_POST)
                    .withPayload(payload)
                    .withHeader(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .build();

            return yotiHttpRequest.execute(CreateSessionResult.class);
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
     * @param authStrategy the {@code AuthStrategy}
     * @param sessionId    the session ID
     * @return the session state
     * @throws DocScanException if there was an error
     */
    public GetSessionResult retrieveSession(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createYotiDocsSessionPath(sessionId);
        LOG.info("Fetching session from '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return yotiHttpRequest.execute(GetSessionResult.class);
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
     * @param authStrategy the {@code AuthStrategy}
     * @param sessionId    the session ID
     * @throws DocScanException if there was an error
     */
    public void deleteSession(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createYotiDocsSessionPath(sessionId);
        LOG.info("Deleting session from '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_DELETE)
                    .build();

            yotiHttpRequest.execute();
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
     * @param authStrategy the {@code AuthStrategy}
     * @param sessionId    the session ID
     * @param mediaId      the media ID
     * @return the {@code Media} content, null if 204 No Content
     * @throws DocScanException if there was an error
     */
    public Media getMediaContent(AuthStrategy authStrategy, String sessionId, String mediaId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");
        notNullOrEmpty(mediaId, "mediaId");

        String path = unsignedPathFactory.createMediaContentPath(sessionId, mediaId);
        LOG.info("Fetching media from '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();
            YotiHttpResponse response = yotiHttpRequest.execute();

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
     * @param authStrategy the {@code AuthStrategy}
     * @param sessionId    the session ID
     * @param mediaId      the media ID
     * @throws DocScanException if there was an error
     */
    public void deleteMediaContent(AuthStrategy authStrategy, String sessionId, String mediaId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");
        notNullOrEmpty(mediaId, "mediaId");

        String path = unsignedPathFactory.createMediaContentPath(sessionId, mediaId);
        LOG.info("Deleting media at '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_DELETE)
                    .build();

            yotiHttpRequest.execute();
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the DELETE: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public void putIbvInstructions(AuthStrategy authStrategy, String sessionId, Instructions instructions) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");
        notNull(instructions, "instructions");

        String path = unsignedPathFactory.createPutIbvInstructionsPath(sessionId);
        LOG.info("Setting IBV instructions at '{}'", path);

        try {
            byte[] payload = objectMapper.writeValueAsBytes(instructions);

            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_PUT)
                    .withPayload(payload)
                    .build();

            yotiHttpRequest.execute();
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the PUT: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public InstructionsResponse getIbvInstructions(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createFetchIbvInstructionsPath(sessionId);
        LOG.info("Fetching IBV instructions at '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return yotiHttpRequest.execute(InstructionsResponse.class);
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the current state of a given session
     *
     * @param authStrategy the {@code AuthStrategy}
     * @param sessionId    the session ID
     * @return the session state
     * @throws DocScanException if there was an error
     */
    public ContactProfileResponse fetchInstructionsContactProfile(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createFetchInstructionsContactProfilePath(sessionId);
        LOG.info("Fetching instruction contact profile from '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return yotiHttpRequest.execute(ContactProfileResponse.class);
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public Media getIbvInstructionsPdf(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createFetchIbvInstructionsPdfPath(sessionId);
        LOG.info("Fetching instructions PDF at '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();
            YotiHttpResponse response = yotiHttpRequest.execute();

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

    public SessionConfigurationResponse fetchSessionConfiguration(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createFetchSessionConfigurationPath(sessionId);
        LOG.info("Fetching session configuration from '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return yotiHttpRequest.execute(SessionConfigurationResponse.class);
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public CreateFaceCaptureResourceResponse createFaceCaptureResource(AuthStrategy authStrategy,
            String sessionId,
            CreateFaceCaptureResourcePayload createFaceCaptureResourcePayload) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");
        notNull(createFaceCaptureResourcePayload, "createFaceCaptureResourcePayload");

        String path = unsignedPathFactory.createNewFaceCaptureResourcePath(sessionId);
        LOG.info("Creating new Face Capture resource at '{}'", path);

        try {
            byte[] payload = objectMapper.writeValueAsBytes(createFaceCaptureResourcePayload);

            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withPayload(payload)
                    .withHttpMethod(HTTP_POST)
                    .build();

            return yotiHttpRequest.execute(CreateFaceCaptureResourceResponse.class);
        } catch (GeneralSecurityException ex) {
            throw new DocScanException("Error signing the request: " + ex.getMessage(), ex);
        } catch (ResourceException ex) {
            throw new DocScanException("Error executing the POST: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public void uploadFaceCaptureImage(AuthStrategy authStrategy, String sessionId, String resourceId, UploadFaceCaptureImagePayload faceCaptureImagePayload)
            throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");
        notNullOrEmpty(resourceId, "resourceId");
        notNull(faceCaptureImagePayload, "faceCaptureImagePayload");

        String path = unsignedPathFactory.createUploadFaceCaptureImagePath(sessionId, resourceId);
        LOG.info("Uploading image to Face Capture resource at '{}'", path);

        try {
            yotiHttpRequestBuilderFactory.create()
                    .withMultipartBoundary(YOTI_MULTIPART_BOUNDARY)
                    .withMultipartBinaryBody(
                            "binary-content",
                            faceCaptureImagePayload.getImageContents(),
                            ContentType.parse(faceCaptureImagePayload.getImageContentType()),
                            "face-capture-image")
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_PUT)
                    .build()
                    .execute();
        } catch (GeneralSecurityException | ResourceException ex) {
            throw new DocScanException("Error executing the PUT: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    // Surely this needs no auth at all....?
    public SupportedDocumentsResponse getSupportedDocuments(AuthStrategy authStrategy, boolean includeNonLatin) throws DocScanException {
        notNull(authStrategy, "authStrategy");

        String path = unsignedPathFactory.createGetSupportedDocumentsPath(includeNonLatin);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return yotiHttpRequest.execute(SupportedDocumentsResponse.class);
        } catch (GeneralSecurityException | ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public void triggerIbvEmailNotification(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createTriggerIbvEmailNotificationPath(sessionId);
        LOG.info("Triggering IBV email notification at '{}'", path);

        try {
            yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_POST)
                    .build()
                    .execute();
        } catch (GeneralSecurityException | ResourceException ex) {
            throw new DocScanException("Error executing the POST: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public List<MetadataResponse> getTrackedDevices(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createFetchTrackedDevices(sessionId);
        LOG.info("Fetching tracked devices at '{}'", path);

        try {
            YotiHttpRequest yotiHttpRequest = yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_GET)
                    .build();

            return yotiHttpRequest.execute(METADATA_RESPONSE_TYPE_REF);
        } catch (GeneralSecurityException | ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    public void deleteTrackedDevices(AuthStrategy authStrategy, String sessionId) throws DocScanException {
        notNull(authStrategy, "authStrategy");
        notNullOrEmpty(sessionId, "sessionId");

        String path = unsignedPathFactory.createDeleteTrackedDevices(sessionId);
        LOG.info("Deleting tracked devices at '{}'", path);

        try {
            yotiHttpRequestBuilderFactory.create()
                    .withAuthStrategy(authStrategy)
                    .withBaseUrl(apiUrl)
                    .withEndpoint(path)
                    .withHttpMethod(HTTP_DELETE)
                    .build()
                    .execute();
        } catch (GeneralSecurityException | ResourceException ex) {
            throw new DocScanException("Error executing the GET: " + ex.getMessage(), ex);
        } catch (IOException | URISyntaxException ex) {
            throw new DocScanException("Error building the request: " + ex.getMessage(), ex);
        }
    }

    private String findContentType(YotiHttpResponse response) {
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

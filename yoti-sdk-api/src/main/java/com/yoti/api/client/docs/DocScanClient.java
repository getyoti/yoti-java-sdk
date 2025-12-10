package com.yoti.api.client.docs;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;
import java.util.List;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
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
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;
import com.yoti.api.client.spi.remote.call.factory.AuthTokenStrategy;
import com.yoti.api.client.spi.remote.call.factory.DocsSignedRequestStrategy;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client used for communication with the Yoti Doc Scan service
 * <p>
 * The {@code DocScanClient} facilitates requests to the Yoti Doc Scan service
 */
public class DocScanClient {

    private static final Logger LOG = LoggerFactory.getLogger(DocScanClient.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final AuthStrategy authStrategy;
    private final DocScanService docScanService;

    private DocScanClient(AuthStrategy authStrategy, DocScanService docScanService) {
        this.authStrategy = authStrategy;
        this.docScanService = docScanService;
    }

    public static DocScanClient.Builder builder() {
        return new DocScanClient.Builder();
    }

    /**
     * Creates a Doc Scan session using the supplied session specification
     *
     * @param sessionSpec the Doc Scan session specification
     * @return {@link CreateSessionResult} the session creation result
     * @throws DocScanException if an error has occurred
     */
    public CreateSessionResult createSession(SessionSpec sessionSpec) throws DocScanException {
        LOG.debug("Creating a YotiDocs session...");
        return docScanService.createSession(authStrategy, sessionSpec);
    }

    /**
     * Retrieves the state of a previously created Yoti Doc Scan session
     *
     * @param sessionId the session ID
     * @return {@link GetSessionResult} the session state
     * @throws DocScanException if an error has occurred
     */
    public GetSessionResult getSession(String sessionId) throws DocScanException {
        LOG.debug("Retrieving session '{}'", sessionId);
        return docScanService.retrieveSession(authStrategy, sessionId);
    }

    /**
     * Deletes a previously created Yoti Doc Scan session and all
     * of its related resources
     *
     * @param sessionId the session ID
     * @throws DocScanException if an error has occurred
     */
    public void deleteSession(String sessionId) throws DocScanException {
        LOG.debug("Deleting session '{}'", sessionId);
        docScanService.deleteSession(authStrategy, sessionId);
    }

    /**
     * Retrieves media related to a Yoti Doc Scan session based
     * on the supplied media ID
     *
     * @param sessionId the session ID
     * @param mediaId   the media ID
     * @return {@link Media} the media
     * @throws DocScanException if an error has occurred
     */
    public Media getMediaContent(String sessionId, String mediaId) throws DocScanException {
        LOG.debug("Retrieving media content '{}' in session '{}'", mediaId, sessionId);
        return docScanService.getMediaContent(authStrategy, sessionId, mediaId);
    }

    /**
     * Deletes media related to a Yoti Doc Scan session based
     * on the supplied media ID
     *
     * @param sessionId the session ID
     * @param mediaId   the media ID
     * @throws DocScanException if an error has occurred
     */
    public void deleteMediaContent(String sessionId, String mediaId) throws DocScanException {
        LOG.debug("Deleting media content '{}' in session '{}'", mediaId, sessionId);
        docScanService.deleteMediaContent(authStrategy, sessionId, mediaId);
    }

    /**
     * Sets the IBV instructions for the given session
     *
     * @param sessionId the session ID
     * @param instructions the instructions
     * @throws DocScanException if an error has occurred
     */
    public void putIbvInstructions(String sessionId, Instructions instructions) throws DocScanException {
        LOG.debug("Setting IBV instructions for session '{}'", sessionId);
        docScanService.putIbvInstructions(authStrategy, sessionId, instructions);
    }

    /**
     * Fetches the instructions PDF associated with an In-Branch Verification session.
     *
     * @param sessionId the sessionID
     * @return the PDF media
     * @throws DocScanException if an error has occurred
     */
    public Media getIbvInstructionsPdf(String sessionId) throws DocScanException {
        LOG.debug("Retrieving IBV instructions PDF in session '{}'", sessionId);
        return docScanService.getIbvInstructionsPdf(authStrategy, sessionId);
    }

    /**
     * Fetches the associated instructions contact profile for the given In-Branch Verification session
     *
     * @param sessionId the session ID
     * @return the contact profile
     * @throws DocScanException if an error has occurred
     */
    public ContactProfileResponse fetchInstructionsContactProfile(String sessionId) throws DocScanException {
        LOG.debug("Fetching instructions contact profile in session '{}'", sessionId);
        return docScanService.fetchInstructionsContactProfile(authStrategy, sessionId);
    }

    /**
     * Creates a Face Capture resource, that will be linked using
     * the supplied requirement ID
     *
     * @param sessionId the session ID
     * @param createFaceCaptureResourcePayload the {@link CreateFaceCaptureResourcePayload}
     * @return the response
     * @throws DocScanException if an error has occurred
     */
    public CreateFaceCaptureResourceResponse createFaceCaptureResource(String sessionId, CreateFaceCaptureResourcePayload createFaceCaptureResourcePayload) throws DocScanException {
        LOG.debug("Creating Face Capture resource in session '{}' for requirement '{}'", sessionId, createFaceCaptureResourcePayload.getRequirementId());
        return docScanService.createFaceCaptureResource(authStrategy, sessionId, createFaceCaptureResourcePayload);
    }

    /**
     * Uploads an image to the specified Face Capture resource
     *
     * @param sessionId the session ID
     * @param uploadFaceCaptureImagePayload the Face Capture image payload
     * @throws DocScanException if an error has occurred
     */
    public void uploadFaceCaptureImage(String sessionId, String resourceId, UploadFaceCaptureImagePayload uploadFaceCaptureImagePayload) throws DocScanException {
        LOG.debug("Uploading image to Face Capture resource '{}' for session '{}'", resourceId, sessionId);
        docScanService.uploadFaceCaptureImage(authStrategy, sessionId, resourceId, uploadFaceCaptureImagePayload);
    }

    /**
     * Gets a list of supported documents.
     *
     * @param includeNonLatin the includeNonLatin flag
     * @return the supported documents
     * @throws DocScanException if an error has occurred
     */
    public SupportedDocumentsResponse getSupportedDocuments(boolean includeNonLatin) throws DocScanException {
        LOG.debug("Getting all supported documents");
        return docScanService.getSupportedDocuments(includeNonLatin);
    }

    /**
     * Gets a list of supported documents only with latin documents.
     *
     * @return the supported documents
     * @throws DocScanException if an error has occurred
     */
    public SupportedDocumentsResponse getSupportedDocuments() throws DocScanException {
        return getSupportedDocuments(false);
    }

    /**
     * Fetches any currently set instructions for an IBV session.
     *
     * @return the instructions
     * @throws DocScanException if an error has occurred
     */
    public InstructionsResponse getIbvInstructions(String sessionId) throws DocScanException {
        LOG.debug("Fetching instructions for session '{}'", sessionId);
        return docScanService.getIbvInstructions(authStrategy, sessionId);
    }

    /**
     * Triggers an email notification for the IBV instructions at-home flow.
     * This will be one of:
     *  - an email sent directly to the end user, using the email provided in the ContactProfile
     *  - if requested, a backend notification using the configured notification endpoint
     *
     * @param sessionId the session ID
     * @throws DocScanException if an error has occurred
     */
    public void triggerIbvEmailNotification(String sessionId) throws DocScanException {
        LOG.debug("Triggering IBV email notification for session '{}'", sessionId);
        docScanService.triggerIbvEmailNotification(authStrategy, sessionId);
    }

    /**
     * Fetches the configuration for the given sessionID.
     *
     * @param sessionId the session ID
     *
     * @return the session configuration
     * @throws DocScanException if an error has occurred
     */
    public SessionConfigurationResponse getSessionConfiguration(String sessionId) throws DocScanException {
        LOG.debug("Fetching configuration for session '{}'", sessionId);
        return docScanService.fetchSessionConfiguration(authStrategy, sessionId);
    }

    /**
     * Fetches details of the devices tracked at key points in completing the session.
     *
     * @param sessionId the session ID
     *
     * @return the list of tracked devices information
     * @throws DocScanException if an error has occurred
     */
    public List<MetadataResponse> getTrackedDevices(String sessionId) throws DocScanException {
        LOG.debug("Fetching tracked devices for session '{}'", sessionId);
        return docScanService.getTrackedDevices(authStrategy, sessionId);
    }

    /**
     * Deletes the tracked devices metadata for the given sessionID.
     *
     * @param sessionId the session ID
     *
     * @throws DocScanException if an error has occurred
     */
    public void deleteTrackedDevices(String sessionId) throws DocScanException {
        LOG.debug("Deleting tracked devices for session '{}'", sessionId);
        docScanService.deleteTrackedDevices(authStrategy, sessionId);
    }

    public static class Builder {

        private static final DocScanService docScanService = DocScanService.newInstance();

        private String authenticationToken;
        private String sdkId;
        private KeyPairSource keyPairSource;

        public Builder withAuthenticationToken(String authenticationToken) {
            this.authenticationToken = authenticationToken;
            return this;
        }

        public Builder withClientSdkId(String sdkId) {
            this.sdkId = sdkId;
            return this;
        }

        public Builder withKeyPairSource(KeyPairSource kps) {
            this.keyPairSource = kps;
            return this;
        }

        public DocScanClient build() {
            if (authenticationToken == null) {
                validateForSignedRequest();
                KeyPair keyPair = loadKeyPair(keyPairSource);
                return new DocScanClient(new DocsSignedRequestStrategy(keyPair, sdkId), docScanService);
            } else {
                validateAuthToken();
                return new DocScanClient(new AuthTokenStrategy(authenticationToken), docScanService);
            }
        }

        private void validateForSignedRequest() {
            if (sdkId == null || sdkId.isEmpty() || keyPairSource == null) {
                throw new IllegalStateException("An sdkId and KeyPairSource must be provided when not using an authentication token");
            }
        }

        private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
            try {
                LOG.debug("Loading key pair from '{}'", kpSource);
                return kpSource.getFromStream(new KeyStreamVisitor());
            } catch (IOException e) {
                throw new InitialisationException("Cannot load key pair", e);
            }
        }

        private void validateAuthToken() {
            if (sdkId != null || keyPairSource != null) {
                throw new IllegalStateException("Must not supply sdkId or KeyPairSource when using an authentication token");
            }
        }

    }

}

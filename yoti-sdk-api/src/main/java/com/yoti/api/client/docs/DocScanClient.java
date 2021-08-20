package com.yoti.api.client.docs;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.instructions.Instructions;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.docs.session.retrieve.instructions.InstructionsResponse;
import com.yoti.api.client.docs.support.SupportedDocumentsResponse;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;

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

    private final String sdkId;
    private final KeyPair keyPair;

    private final DocScanService docScanService;

    DocScanClient(final String sdkId,
            final KeyPairSource keyPairSource,
            DocScanService docScanService) {
        this.sdkId = sdkId;
        this.keyPair = loadKeyPair(keyPairSource);
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
        return docScanService.createSession(sdkId, keyPair, sessionSpec);
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
        return docScanService.retrieveSession(sdkId, keyPair, sessionId);
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
        docScanService.deleteSession(sdkId, keyPair, sessionId);
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
        return docScanService.getMediaContent(sdkId, keyPair, sessionId, mediaId);
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
        docScanService.deleteMediaContent(sdkId, keyPair, sessionId, mediaId);
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
        docScanService.putIbvInstructions(sdkId, keyPair, sessionId, instructions);
    }

    /**
     * Gets a list of supported documents.
     *
     * @return the supported documents
     * @throws DocScanException if an error has occurred
     */
    public SupportedDocumentsResponse getSupportedDocuments() throws DocScanException {
        LOG.debug("Getting all supported documents");
        return docScanService.getSupportedDocuments(keyPair);
    }

    /**
     * Fetches any currently set instructions for an IBV session.
     *
     * @return the instructions
     */
    public InstructionsResponse getIbvInstructions(String sessionId) throws DocScanException {
        LOG.debug("Fetching instructions for session '{}'", sessionId);
        return docScanService.getIbvInstructions(sdkId, keyPair, sessionId);
    }

    private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            LOG.debug("Loading key pair from '{}'", kpSource);
            return kpSource.getFromStream(new KeyStreamVisitor());
        } catch (IOException e) {
            throw new InitialisationException("Cannot load key pair", e);
        }
    }

    public static class Builder {

        private static final DocScanService docScanService = DocScanService.newInstance();

        private String sdkId;
        private KeyPairSource keyPairSource;

        public Builder withClientSdkId(String sdkId) {
            this.sdkId = sdkId;
            return this;
        }

        public Builder withKeyPairSource(KeyPairSource kps) {
            this.keyPairSource = kps;
            return this;
        }

        public DocScanClient build() {
            notNullOrEmpty(sdkId, "SDK ID");
            notNull(keyPairSource, "Application key Pair");

            return new DocScanClient(
                    sdkId,
                    keyPairSource,
                    docScanService
            );
        }
    }

}

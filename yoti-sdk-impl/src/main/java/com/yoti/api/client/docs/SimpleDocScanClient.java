package com.yoti.api.client.docs;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.docs.support.SupportedDocumentsResponse;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDocScanClient implements DocScanClient {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleDocScanClient.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final String sdkId;
    private final KeyPair keyPair;

    private final DocScanService docScanService;

    SimpleDocScanClient(final String sdkId,
            final KeyPairSource keyPairSource,
            DocScanService docScanService) {
        this.sdkId = sdkId;
        this.keyPair = loadKeyPair(keyPairSource);
        this.docScanService = docScanService;
    }

    @Override
    public CreateSessionResult createSession(SessionSpec sessionSpec) throws DocScanException {
        LOG.debug("Creating a YotiDocs session...");
        return docScanService.createSession(sdkId, keyPair, sessionSpec);
    }

    @Override
    public GetSessionResult getSession(String sessionId) throws DocScanException {
        LOG.debug("Retrieving session '{}'", sessionId);
        return docScanService.retrieveSession(sdkId, keyPair, sessionId);
    }

    @Override
    public void deleteSession(String sessionId) throws DocScanException {
        LOG.debug("Deleting session '{}'", sessionId);
        docScanService.deleteSession(sdkId, keyPair, sessionId);
    }

    @Override
    public Media getMediaContent(String sessionId, String mediaId) throws DocScanException {
        LOG.debug("Retrieving media content '{}' in session '{}'", mediaId, sessionId);
        return docScanService.getMediaContent(sdkId, keyPair, sessionId, mediaId);
    }

    @Override
    public void deleteMediaContent(String sessionId, String mediaId) throws DocScanException {
        LOG.debug("Deleting media content '{}' in session '{}'", mediaId, sessionId);
        docScanService.deleteMediaContent(sdkId, keyPair, sessionId, mediaId);
    }

    /**
     * Gets a list of supported documents.
     *
     * @return the supported documents
     * @throws DocScanException if an error has occurred
     */
    @Override
    public SupportedDocumentsResponse getSupportedDocuments() throws DocScanException {
        LOG.debug("Getting all supported documents");
        return docScanService.getSupportedDocuments(keyPair);
    }

    private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            LOG.debug("Loading key pair from '{}'", kpSource);
            return kpSource.getFromStream(new KeyStreamVisitor());
        } catch (IOException e) {
            throw new InitialisationException("Cannot load key pair", e);
        }
    }

}

package com.yoti.api.client.docs;

import com.yoti.api.client.Media;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.docs.support.SupportedDocumentsResponse;

/**
 * Client used for communication with the Yoti Doc Scan service
 * <p>
 * The {@code DocScanClient} facilitates requests to the Yoti Doc Scan service
 */
public interface DocScanClient {

    /**
     * Creates a Doc Scan session using the supplied session specification
     *
     * @param sessionSpec the Doc Scan session specification
     * @return {@link CreateSessionResult} the session creation result
     * @throws DocScanException if an error has occurred
     */
    CreateSessionResult createSession(SessionSpec sessionSpec) throws DocScanException;

    /**
     * Retrieves the state of a previously created Yoti Doc Scan session
     *
     * @param sessionId the session ID
     * @return {@link GetSessionResult} the session state
     * @throws DocScanException if an error has occurred
     */
    GetSessionResult getSession(String sessionId) throws DocScanException;

    /**
     * Deletes a previously created Yoti Doc Scan session and all
     * of its related resources
     *
     * @param sessionId the session ID
     * @throws DocScanException if an error has occurred
     */
    void deleteSession(String sessionId) throws DocScanException;

    /**
     * Retrieves media related to a Yoti Doc Scan session based
     * on the supplied media ID
     *
     * @param sessionId the session ID
     * @param mediaId   the media ID
     * @return {@link Media} the media
     * @throws DocScanException if an error has occurred
     */
    Media getMediaContent(String sessionId, String mediaId) throws DocScanException;

    /**
     * Deletes media related to a Yoti Doc Scan session based
     * on the supplied media ID
     *
     * @param sessionId the session ID
     * @param mediaId   the media ID
     * @throws DocScanException if an error has occurred
     */
    void deleteMediaContent(String sessionId, String mediaId)  throws DocScanException;

    /**
     * Gets a list of supported documents.
     *
     * @return the supported documents
     * @throws DocScanException if an error has occurred
     */
    SupportedDocumentsResponse getSupportedDocuments() throws DocScanException;

}

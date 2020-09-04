package com.yoti.api.client.docs.session.create.check;

/**
 * The configuration applied when creating a DocumentAuthenticityCheck
 */
public interface RequestedDocumentAuthenticityConfig extends RequestedCheckConfig {

    /**
     * Returns the value for a manual check for a given
     * document authenticity check.
     *
     * @return the manual check value
     */
    String getManualCheck();

}

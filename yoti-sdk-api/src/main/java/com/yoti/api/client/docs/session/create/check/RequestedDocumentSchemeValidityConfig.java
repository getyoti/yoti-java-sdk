package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating a DocumentSchemeValidityCheck
 */
public class RequestedDocumentSchemeValidityConfig implements RequestedCheckConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    @JsonProperty("scheme")
    private final String scheme;

    RequestedDocumentSchemeValidityConfig(String manualCheck, String scheme) {
        this.manualCheck = manualCheck;
        this.scheme = scheme;
    }

    /**
     * Returns the value for a manual check for a given
     * document scheme validity check.
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

    /**
     * Returns the value for a scheme for a given
     * document scheme validity check.
     *
     * @return the manual check value
     */
    public String getScheme() {
        return scheme;
    }
}

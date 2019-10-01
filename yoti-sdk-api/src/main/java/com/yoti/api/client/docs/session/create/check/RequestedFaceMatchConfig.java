package com.yoti.api.client.docs.session.create.check;

/**
 * The configuration applied when creating a FaceMatchCheck
 */
public interface RequestedFaceMatchConfig extends RequestedCheckConfig {

    /**
     * Returns the value for a manual check for a given
     * face match.
     *
     * @return the manual check value
     */
    String getManualCheck();

}

package com.yoti.api.client.qrcode;

/**
 * URL to share with a 3rd party
 *
 */
public interface ShareUrlResult {

    /**
     * The URL the 3rd party should use for the share
     *
     * @return The share URL
     */
    String getShareUrl();

    /**
     * Get the Yoti reference id for the share
     *
     * @return reference id for the share
     */
    String getRefId();

}

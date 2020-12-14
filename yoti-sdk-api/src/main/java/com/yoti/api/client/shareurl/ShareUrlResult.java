package com.yoti.api.client.shareurl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * URL to share with a 3rd party
 *
 */
public class ShareUrlResult {

    @JsonProperty("qrcode")
    private String shareUrl;

    @JsonProperty("ref_id")
    private String refId;

    /**
     * The URL that the 3rd party should use for the share
     *
     * @return The share URL
     */
    public String getUrl() {
        return shareUrl;
    }

    /**
     * Get the Yoti reference id for the share
     *
     * @return reference id for the share
     */
    public String getRefId() {
        return refId;
    }

}

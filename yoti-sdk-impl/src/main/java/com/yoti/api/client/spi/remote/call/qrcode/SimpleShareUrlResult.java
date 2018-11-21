package com.yoti.api.client.spi.remote.call.qrcode;

import com.yoti.api.client.qrcode.ShareUrlResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShareUrlResult implements ShareUrlResult {

    @JsonProperty("qrcode")
    private String shareUrl;

    @JsonProperty("ref_id")
    private String refId;

    public String getUrl() {
        return shareUrl;
    }

    public String getRefId() {
        return refId;
    }

    @Override
    public String toString() {
        return "SimpleShareUrlResult{" +
                "shareUrl='" + shareUrl + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
    
}

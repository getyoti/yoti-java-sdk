package com.yoti.api.client.spi.remote.call.qrcode;

import com.yoti.api.client.qrcode.QrCodeResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleQrCodeResult implements QrCodeResult {

    @JsonProperty("qrcode")
    private String qrCodeUrl;

    @JsonProperty("ref_id")
    private String refId;

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public String getRefId() {
        return refId;
    }

    @Override
    public String toString() {
        return "SimpleQrCodeResult{" +
                "qrCodeUrl='" + qrCodeUrl + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
    
}

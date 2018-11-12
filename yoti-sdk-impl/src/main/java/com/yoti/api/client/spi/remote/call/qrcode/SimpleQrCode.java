package com.yoti.api.client.spi.remote.call.qrcode;

import com.yoti.api.client.qrcode.QrCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleQrCode implements QrCode {

    @JsonProperty("qrcode")
    private String qrCode;

    @JsonProperty("ref_id")
    private String refId;

    public String getQrCode() {
        return qrCode;
    }

    public String getRefId() {
        return refId;
    }

    @Override
    public String toString() {
        return "SimpleQrCode{" +
                "qrCode='" + qrCode + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
    
}

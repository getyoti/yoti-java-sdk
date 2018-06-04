package com.yoti.api.client.spi.remote.call.qrcode;

import com.yoti.api.client.qrcode.DynamicQRCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleDynamicQRCode implements DynamicQRCode {

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
        return "SimpleDynamicQRCode{" +
                "qrCode='" + qrCode + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
    
}

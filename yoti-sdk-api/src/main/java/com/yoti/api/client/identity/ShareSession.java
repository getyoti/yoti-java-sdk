package com.yoti.api.client.identity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareSession {

    private String id;
    private String status;
    private String created;
    private String updated;
    private String expiry;
    private String qrCodeId;
    private String receiptId;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    @JsonProperty(Property.ID)
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty(Property.STATUS)
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty(Property.CREATED)
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty(Property.UPDATED)
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty(Property.EXPIRY)
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @JsonProperty(Property.QR_CODE)
    public void setQrCodeId(Map<String, Object> qrCode) {
        this.qrCodeId = (String) qrCode.get(Property.ID);
    }

    @JsonProperty(Property.RECEIPT)
    public void setReceiptId(Map<String, Object> receipt) {
        this.receiptId = (String) receipt.get(Property.ID);
    }

    private static final class Property {

        private static final String ID = "id";
        private static final String CREATED = "created";
        private static final String UPDATED = "updated";
        private static final String STATUS = "status";
        private static final String EXPIRY = "expiry";
        private static final String QR_CODE = "qrCode";
        private static final String RECEIPT = "receipt";

    }

}

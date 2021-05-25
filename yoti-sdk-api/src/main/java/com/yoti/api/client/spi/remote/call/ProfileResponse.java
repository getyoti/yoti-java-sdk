package com.yoti.api.client.spi.remote.call;

import com.fasterxml.jackson.annotation.JsonProperty;

class ProfileResponse {

    @JsonProperty("session_data")
    private String sessionData;

    @JsonProperty("receipt")
    private Receipt receipt;

    public ProfileResponse(){}

    private ProfileResponse(String sessionData, Receipt receipt){
        this.sessionData = sessionData;
        this.receipt = receipt;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileResponse)) return false;

        ProfileResponse that = (ProfileResponse) o;

        if (!sessionData.equals(that.sessionData)) return false;
        return receipt.equals(that.receipt);

    }

    @Override
    public int hashCode() {
        int result = sessionData.hashCode();
        result = 31 * result + receipt.hashCode();
        return result;
    }

    public static class ProfileResponseBuilder {
        private String sessionData;
        private Receipt receipt;

        public ProfileResponseBuilder setSessionData(String sessionData) {
            this.sessionData = sessionData;
            return this;
        }

        public ProfileResponseBuilder setReceipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        public ProfileResponse createProfileResonse() {
            return new ProfileResponse(sessionData, receipt);
        }
    }

}

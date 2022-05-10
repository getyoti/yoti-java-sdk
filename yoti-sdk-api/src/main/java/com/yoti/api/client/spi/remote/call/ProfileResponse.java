package com.yoti.api.client.spi.remote.call;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileResponse {

    @JsonProperty(Property.SESSION_DATA)
    private String sessionData;

    @JsonProperty(Property.RECEIPT)
    private Receipt receipt;

    @JsonProperty(Property.ERROR_DETAILS)
    private ErrorDetails error;

    public ProfileResponse() { }

    private ProfileResponse(String sessionData, Receipt receipt, ErrorDetails error) {
        this.sessionData = sessionData;
        this.receipt = receipt;
        this.error = error;
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

    public ErrorDetails getError() {
        return error;
    }

    public void setError(ErrorDetails error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileResponse that = (ProfileResponse) o;
        return Objects.equals(sessionData, that.sessionData)
                && Objects.equals(receipt, that.receipt)
                && Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionData, receipt, error);
    }

    public static class ProfileResponseBuilder {

        private String sessionData;
        private Receipt receipt;
        private ErrorDetails error;

        public ProfileResponseBuilder setSessionData(String sessionData) {
            this.sessionData = sessionData;
            return this;
        }

        public ProfileResponseBuilder setReceipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        public ProfileResponseBuilder setError(ErrorDetails error) {
            this.error = error;
            return this;
        }

        /**
         * ProfileResponse Builder build
         *
         * @return The response Profile
         * @deprecated Use {@link #build()} instead.
         */
        @Deprecated
        public ProfileResponse createProfileResonse() {
            return new ProfileResponse(sessionData, receipt, error);
        }

        public ProfileResponse build() {
            return new ProfileResponse(sessionData, receipt, error);
        }

    }

    private static final class Property {

        private static final String SESSION_DATA = "session_data";
        private static final String RECEIPT = "receipt";
        private static final String ERROR_DETAILS = "error_details";

        private Property() { }

    }

}

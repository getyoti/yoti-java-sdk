package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportTokenPayload {

    @JsonProperty("ttl")
    private Integer ttl;

    private ImportTokenPayload(Integer ttl) {
        this.ttl = ttl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getTtl() {
        return ttl;
    }

    public static class Builder {

        private Integer ttl;

        private Builder() { }

        /**
         * Set import token time-to-live.
         *
         * @param ttl the time-to-live value
         * @return the builder
         */
        public Builder withTtl(int ttl) {
            this.ttl = ttl;
            return this;
        }

        public ImportTokenPayload build() {
            return new ImportTokenPayload(ttl);
        }

    }

}

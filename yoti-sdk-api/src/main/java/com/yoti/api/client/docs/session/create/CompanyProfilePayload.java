package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyProfilePayload {

    @JsonProperty("company_name")
    private final String companyName;

    private CompanyProfilePayload(String companyName) {
        this.companyName = companyName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCompanyName() {
        return companyName;
    }

    public static class Builder {

        private String companyName;

        Builder() {}

        /**
         * Sets the company name to be used for the session
         *
         * @param companyName the company name
         * @return the builder
         */
        public Builder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CompanyProfilePayload build() {
            return new CompanyProfilePayload(companyName);
        }

    }

}

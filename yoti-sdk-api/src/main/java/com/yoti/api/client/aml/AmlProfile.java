package com.yoti.api.client.aml;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AmlProfile {

    @JsonProperty("given_names")
    private final String givenNames;

    @JsonProperty("family_name")
    private final String familyName;

    @JsonProperty("ssn")
    private final String ssn;

    @JsonProperty("address")
    private final AmlAddress amlAddress;

    AmlProfile(String givenNames, String familyName, String ssn, AmlAddress amlAddress) {
        this.givenNames = givenNames;
        this.familyName = familyName;
        this.ssn = ssn;
        this.amlAddress = amlAddress;
    }

    public static AmlProfile.Builder builder() {
        return new AmlProfile.Builder();
    }

    public String getGivenNames() {
        return givenNames;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getSsn() {
        return ssn;
    }

    public AmlAddress getAmlAddress() {
        return amlAddress;
    }

    public static class Builder {

        private String givenNames;
        private String familyName;
        private String ssn;
        private AmlAddress amlAddress;

        public Builder withGivenNames(String givenNames) {
            this.givenNames = givenNames;
            return this;
        }

        public Builder withFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder withSsn(String ssn) {
            this.ssn = ssn;
            return this;
        }

        public Builder withAddress(AmlAddress amlAddress) {
            this.amlAddress = amlAddress;
            return this;
        }

        public AmlProfile build() {
            return new AmlProfile(givenNames, familyName, ssn, amlAddress);
        }

    }

}

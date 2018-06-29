package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.aml.AmlAddress;
import com.yoti.api.client.aml.AmlProfile;

public class SimpleAmlProfile implements AmlProfile {

    @JsonProperty("given_names")
    private final String givenNames;

    @JsonProperty("family_name")
    private final String familyName;

    @JsonProperty("ssn")
    private final String ssn;

    @JsonProperty("address")
    private final AmlAddress amlAddress;

    public SimpleAmlProfile(String givenNames, String familyName, String ssn, AmlAddress amlAddress) {
        this.givenNames = givenNames;
        this.familyName = familyName;
        this.ssn = ssn;
        this.amlAddress = amlAddress;
    }

    @Override
    public String getGivenNames() {
        return givenNames;
    }

    @Override
    public String getFamilyName() {
        return familyName;
    }

    @Override
    public String getSsn() {
        return ssn;
    }

    @Override
    public AmlAddress getAmlAddress() {
        return amlAddress;
    }

}

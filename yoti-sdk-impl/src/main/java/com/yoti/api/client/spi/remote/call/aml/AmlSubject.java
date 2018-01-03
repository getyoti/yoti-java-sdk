package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.aml.AmlProfile;

public class AmlSubject implements AmlProfile {

    @JsonProperty("given_names")
    private final String givenNames;

    @JsonProperty("family_name")
    private final String familyName;

    @JsonProperty("ssn")
    private final String ssn;

    @JsonProperty("address")
    private final AmlAddress amlAddress;

    public AmlSubject(String givenNames, String familyName, String ssn, AmlAddress amlAddress) {
        this.givenNames = givenNames;
        this.familyName = familyName;
        this.ssn = ssn;
        this.amlAddress = amlAddress;
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

    //FIXME: Add a toString()

}

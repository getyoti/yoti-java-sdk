package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyProfileResponse {

    @JsonProperty("company_name")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

}

package com.yoti.api.client.docs.session.retrieve.instructions.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UkPostOfficeBranchResponse extends BranchResponse {

    @JsonProperty("fad_code")
    private String fadCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("post_code")
    private String postCode;

    @JsonProperty("location")
    private LocationResponse location;

    public String getFadCode() {
        return fadCode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostCode() {
        return postCode;
    }

    public LocationResponse getLocation() {
        return location;
    }

}

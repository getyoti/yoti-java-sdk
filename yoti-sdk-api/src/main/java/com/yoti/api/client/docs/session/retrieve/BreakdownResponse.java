package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BreakdownResponse {

    @JsonProperty("sub_check")
    private String subCheck;

    @JsonProperty("result")
    private String result;

    @JsonProperty("details")
    private List<DetailsResponse> details;

    @JsonProperty("process")
    private String process;

    public String getSubCheck() {
        return subCheck;
    }

    public String getResult() {
        return result;
    }

    public List<? extends DetailsResponse> getDetails() {
        return details;
    }

    public String getProcess() {
        return process;
    }

}

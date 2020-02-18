package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBreakdownResponse implements BreakdownResponse {

    @JsonProperty("sub_check")
    private String subCheck;

    @JsonProperty("result")
    private String result;

    @JsonProperty("details")
    private List<SimpleDetailsResponse> details;

    @Override
    public String getSubCheck() {
        return subCheck;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public List<? extends DetailsResponse> getDetails() {
        return details;
    }

}

package com.yoti.api.client.identity;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class MatchResult {

    @JsonProperty(Property.ID)
    private String id;

    @JsonProperty(Property.RESULT)
    private String result;

    public String getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private static final class Property {

        private static final String ID = "id";
        private static final String RESULT = "result";

    }

}

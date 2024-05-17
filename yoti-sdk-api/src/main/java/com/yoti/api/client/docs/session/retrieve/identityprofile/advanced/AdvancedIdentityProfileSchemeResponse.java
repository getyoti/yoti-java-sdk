package com.yoti.api.client.docs.session.retrieve.identityprofile.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileSchemeResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("objective")
    private String objective;

    @JsonProperty("label")
    private String label;

    public String getType() {
        return type;
    }

    public String getObjective() {
        return objective;
    }

    public String getLabel() {
        return label;
    }

}

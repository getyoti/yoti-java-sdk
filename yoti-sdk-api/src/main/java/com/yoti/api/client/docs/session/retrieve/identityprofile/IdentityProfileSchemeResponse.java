package com.yoti.api.client.docs.session.retrieve.identityprofile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileSchemeResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("objective")
    private String objective;

    /**
     * The type of the scheme
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * The objective of the scheme
     *
     * @return the objective
     */
    public String getObjective() {
        return objective;
    }

}

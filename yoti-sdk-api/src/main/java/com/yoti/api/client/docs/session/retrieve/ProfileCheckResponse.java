package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ProfileCheckResponse extends CheckResponse {

    @JsonProperty("generated_profile")
    private GeneratedProfileResponse generatedProfile;

    public GeneratedProfileResponse getGeneratedProfile() {
        return generatedProfile;
    }

}

package com.yoti.api.client.docs.session.create.identityprofile.advanced;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileRequirementsPayload {

    @JsonProperty("profiles")
    private final List<AdvancedIdentityProfilePayload> profiles;

    private AdvancedIdentityProfileRequirementsPayload(List<AdvancedIdentityProfilePayload> profiles) {
        this.profiles = profiles;
    }

    public static AdvancedIdentityProfileRequirementsPayload.Builder builder() {
        return new AdvancedIdentityProfileRequirementsPayload.Builder();
    }

    /**
     * Returns the list of profiles that have been requested as part of the Advanced Identity Profile
     *
     * @return the list of profiles
     */
    public List<AdvancedIdentityProfilePayload> getProfiles() {
        return profiles;
    }

    public static final class Builder {

        private List<AdvancedIdentityProfilePayload> profiles;

        private Builder() {
            profiles = new ArrayList<>();
        }

        public Builder withProfile(AdvancedIdentityProfilePayload profile) {
            this.profiles.add(profile);
            return this;
        }

        public AdvancedIdentityProfileRequirementsPayload build() {
            return new AdvancedIdentityProfileRequirementsPayload(profiles);
        }

    }

}

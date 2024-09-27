package com.yoti.api.client.docs.session.create.identityprofile.simple;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentityProfileRequirementsPayload {

    @JsonProperty("trust_framework")
    private final String trustFramework;

    @JsonProperty("scheme")
    private final IdentityProfileSchemePayload scheme;

    IdentityProfileRequirementsPayload(String trustFramework, IdentityProfileSchemePayload scheme) {
        this.trustFramework = trustFramework;
        this.scheme = scheme;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTrustFramework() {
        return trustFramework;
    }

    public IdentityProfileSchemePayload getScheme() {
        return scheme;
    }

    public static class Builder {

        private String trustFramework;
        private IdentityProfileSchemePayload scheme;

        Builder() {}

        /**
         * Sets the trust framework name for the Identity Profile requirement
         *
         * @param trustFramework the name of the trust framework
         * @return the builder
         */
        public Builder withTrustFramework(String trustFramework) {
            this.trustFramework = trustFramework;
            return this;
        }

        /**
         * Sets the scheme for the Identity Profile requirement
         *
         * @param scheme the scheme
         * @return the builder
         */
        public Builder withScheme(IdentityProfileSchemePayload scheme) {
            this.scheme = scheme;
            return this;
        }

        public IdentityProfileRequirementsPayload build() {
            return new IdentityProfileRequirementsPayload(trustFramework, scheme);
        }

    }

}

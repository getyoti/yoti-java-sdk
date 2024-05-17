package com.yoti.api.client.docs.session.create.identityprofile.advanced;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfilePayload {

    @JsonProperty("trust_framework")
    private final String trustFramework;

    @JsonProperty("schemes")
    private final List<AdvancedIdentityProfileSchemePayload> schemes;

    private AdvancedIdentityProfilePayload(String trustFramework, List<AdvancedIdentityProfileSchemePayload> schemes) {
        this.trustFramework = trustFramework;
        this.schemes = schemes;
    }

    public static AdvancedIdentityProfilePayload.Builder builder() {
        return new AdvancedIdentityProfilePayload.Builder();
    }

    /**
     * Returns the trust framework of the profile being requested
     *
     * @return the trust framework
     */
    public String getTrustFramework() {
        return trustFramework;
    }

    /**
     * Returns the schemes being requested under the trust framework
     *
     * @return the schemes
     */
    public List<AdvancedIdentityProfileSchemePayload> getSchemes() {
        return schemes;
    }

    public static final class Builder {

        private String trustFramework;
        private List<AdvancedIdentityProfileSchemePayload> schemes;

        private Builder() {
            schemes = new ArrayList<>();
        }

        /**
         * Sets the trust framework of the requested profile
         *
         * @param trustFramework the trust framework
         * @return the builder
         */
        public Builder withTrustFramework(String trustFramework) {
            this.trustFramework = trustFramework;
            return this;
        }

        /**
         * Adds a scheme to be requested under the trust framework
         *
         * @param scheme the scheme
         * @return the builder
         */
        public Builder withScheme(AdvancedIdentityProfileSchemePayload scheme) {
            this.schemes.add(scheme);
            return this;
        }

        public AdvancedIdentityProfilePayload build() {
            return new AdvancedIdentityProfilePayload(trustFramework, schemes);
        }

    }

}

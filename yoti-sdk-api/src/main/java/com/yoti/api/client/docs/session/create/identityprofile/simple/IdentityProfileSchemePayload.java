package com.yoti.api.client.docs.session.create.identityprofile.simple;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentityProfileSchemePayload {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("objective")
    private final String objective;

    private IdentityProfileSchemePayload(String type, String objective) {
        this.type = type;
        this.objective = objective;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getType() {
        return type;
    }

    public String getObjective() {
        return objective;
    }

    public static class Builder {

        private String type;
        private String objective;

        Builder() {}

        /**
         * Sets the type of the scheme for the Identity Profile
         *
         * @param type the type of scheme
         * @return the builder
         */
        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the objective of the scheme for the Identity Profile
         *
         * @param objective the objective of the scheme
         * @return the builder
         */
        public Builder withObjective(String objective) {
            this.objective = objective;
            return this;
        }

        public IdentityProfileSchemePayload build() {
            return new IdentityProfileSchemePayload(type, objective);
        }

    }

}

package com.yoti.api.client.shareurl.policy.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines which scheme this identity profile should satisfy
 */
public final class IdentityProfileScheme {

    @JsonProperty(Property.TYPE)
    private final String type;

    @JsonProperty(Property.OBJECTIVE)
    private final String objective;

    IdentityProfileScheme(String type, String objective) {
        this.type = type;
        this.objective = objective;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Defines which scheme this identity profile should satisfy.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Defines the objective to be achieved for the particular scheme.
     *
     * @return objective
     */
    public String getObjective() {
        return objective;
    }

    public static final class Builder {

        private String type;
        private String objective;

        private Builder() { }

        public Builder withType(String type) {
            this.type = type;

            return this;
        }

        public Builder withObjective(String objective) {
            this.objective = objective;

            return this;
        }

        public IdentityProfileScheme build() {
            return new IdentityProfileScheme(type, objective);
        }

    }

    private static final class Property {

        private Property() { }

        private static final String TYPE = "type";
        private static final String OBJECTIVE = "objective";

    }

}

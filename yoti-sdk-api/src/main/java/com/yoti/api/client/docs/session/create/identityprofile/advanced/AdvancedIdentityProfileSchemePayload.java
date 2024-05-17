package com.yoti.api.client.docs.session.create.identityprofile.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileSchemePayload {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("objective")
    private final String objective;

    @JsonProperty("label")
    private final String label;

    @JsonProperty("config")
    private final AdvancedIdentityProfileSchemeConfigPayload config;

    private AdvancedIdentityProfileSchemePayload(String type, String objective, String label, AdvancedIdentityProfileSchemeConfigPayload config) {
        this.type = type;
        this.objective = objective;
        this.label = label;
        this.config = config;
    }

    public static AdvancedIdentityProfileSchemePayload.Builder builder() {
        return new AdvancedIdentityProfileSchemePayload.Builder();
    }

    public String getType() {
        return type;
    }

    public String getObjective() {
        return objective;
    }

    public String getLabel() {
        return label;
    }

    public AdvancedIdentityProfileSchemeConfigPayload getConfig() {
        return config;
    }

    public static final class Builder {

        private String type;
        private String objective;
        private String label;
        private AdvancedIdentityProfileSchemeConfigPayload config;

        private Builder() {}

        /**
         * Sets the type of the scheme
         *
         * @param type the type
         * @return the builder
         */
        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the objective of the scheme
         *
         * @param objective the objective
         * @return the builder
         */
        public Builder withObjective(String objective) {
            this.objective = objective;
            return this;
        }

        /**
         * Sets the label for the scheme
         *
         * @param label the label
         * @return the builder
         */
        public Builder withLabel(String label) {
            this.label = label;
            return this;
        }

        /**
         * Sets the configuration for the scheme
         *
         * @param config the configuration
         * @return the builder
         */
        public Builder withConfig(AdvancedIdentityProfileSchemeConfigPayload config) {
            this.config = config;
            return this;
        }

        public AdvancedIdentityProfileSchemePayload build() {
            return new AdvancedIdentityProfileSchemePayload(type, objective, label, config);
        }

    }
}

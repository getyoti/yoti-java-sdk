package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides configuration properties used by the web/native clients
 */
public class SdkConfig {

    @JsonProperty(Property.ALLOWED_CAPTURE_METHODS)
    private final String allowedCaptureMethods;

    @JsonProperty(Property.PRIMARY_COLOUR)
    private final String primaryColour;

    @JsonProperty(Property.SECONDARY_COLOUR)
    private final String secondaryColour;

    @JsonProperty(Property.FONT_COLOUR)
    private final String fontColour;

    @JsonProperty(Property.LOCALE)
    private final String locale;

    @JsonProperty(Property.PRESET_ISSUING_COUNTRY)
    private final String presetIssuingCountry;

    @JsonProperty(Property.SUCCESS_URL)
    private final String successUrl;

    @JsonProperty(Property.ERROR_URL)
    private final String errorUrl;

    @JsonProperty(Property.PRIVACY_POLICY_URL)
    private final String privacyPolicyUrl;

    @JsonProperty(Property.ALLOW_HANDOFF)
    private final Boolean allowHandoff;

    @JsonProperty(Property.ATTEMPTS_CONFIGURATION)
    private final AttemptsConfiguration attemptsConfiguration;

    SdkConfig(String allowedCaptureMethods,
            String primaryColour,
            String secondaryColour,
            String fontColour,
            String locale,
            String presetIssuingCountry,
            String successUrl,
            String errorUrl,
            String privacyPolicyUrl,
            Boolean allowHandoff,
            AttemptsConfiguration attemptsConfiguration) {
        this.allowedCaptureMethods = allowedCaptureMethods;
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        this.fontColour = fontColour;
        this.locale = locale;
        this.presetIssuingCountry = presetIssuingCountry;
        this.successUrl = successUrl;
        this.errorUrl = errorUrl;
        this.privacyPolicyUrl = privacyPolicyUrl;
        this.allowHandoff = allowHandoff;
        this.attemptsConfiguration = attemptsConfiguration;
    }

    public static SdkConfig.Builder builder() {
        return new SdkConfig.Builder();
    }

    /**
     * The methods allowed for capturing document images
     *
     * @return the allowed capture methods
     */
    public String getAllowedCaptureMethods() {
        return allowedCaptureMethods;
    }

    /**
     * The primary colour
     *
     * @return the primary colour
     */
    public String getPrimaryColour() {
        return primaryColour;
    }

    /**
     * The secondary colour
     *
     * @return the secondary colour
     */
    public String getSecondaryColour() {
        return secondaryColour;
    }

    /**
     * The font colour
     *
     * @return the font colour
     */
    public String getFontColour() {
        return fontColour;
    }

    /**
     * The locale
     *
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * The preset issuing country
     *
     * @return the preset issuing country
     */
    public String getPresetIssuingCountry() {
        return presetIssuingCountry;
    }

    /**
     * The success URL
     *
     * @return the success URL
     */
    public String getSuccessUrl() {
        return successUrl;
    }

    /**
     * The error URL
     *
     * @return the error URL
     */
    public String getErrorUrl() {
        return errorUrl;
    }

    /**
     * The privacy policy URL
     *
     * @return the privacy policy URL
     */
    public String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    /**
     * If mobile handoff is allowed in the session
     *
     * @return if mobile handoff is allowed
     */
    public Boolean getAllowHandoff() {
        return allowHandoff;
    }

    /**
     * The number of allowed attempts for certain tasks
     *
     * @return the attempts configuration
     */
    public AttemptsConfiguration getAttemptsConfiguration() {
        return attemptsConfiguration;
    }

    /**
     * Builder to assist in the creation of {@link SdkConfig}.
     */
    public static class Builder {

        private String allowedCaptureMethods;
        private String primaryColour;
        private String secondaryColour;
        private String fontColour;
        private String locale;
        private String presetIssuingCountry;
        private String successUrl;
        private String errorUrl;
        private String privacyPolicyUrl;
        private Boolean allowHandoff;
        private AttemptsConfiguration attemptsConfiguration;

        private Builder() {}

        /**
         * Sets the allowed capture method to "CAMERA"
         *
         * @return the builder
         */
        public Builder withAllowsCamera() {
            return withAllowedCaptureMethods(DocScanConstants.CAMERA);
        }

        /**
         * Sets the allowed capture method to "CAMERA_AND_UPLOAD"
         *
         * @return the builder
         */
        public Builder withAllowsCameraAndUpload() {
            return withAllowedCaptureMethods(DocScanConstants.CAMERA_AND_UPLOAD);
        }

        /**
         * Sets the allowed capture method
         *
         * @param allowedCaptureMethods the allowed capture method
         * @return the builder
         */
        public Builder withAllowedCaptureMethods(String allowedCaptureMethods) {
            this.allowedCaptureMethods = allowedCaptureMethods;
            return this;
        }

        /**
         * Sets the primary colour to be used by the web/native client
         *
         * @param primaryColour the primary colour, hexadecimal value e.g. #ff0000
         * @return the builder
         */
        public Builder withPrimaryColour(String primaryColour) {
            this.primaryColour = primaryColour;
            return this;
        }

        /**
         * Sets the secondary colour to be used by the web/native client (used on the button)
         *
         * @param secondaryColour the secondary colour, hexadecimal value e.g. #ff0000
         * @return the builder
         */
        public Builder withSecondaryColour(String secondaryColour) {
            this.secondaryColour = secondaryColour;
            return this;
        }

        /**
         * Sets the font colour to be used by the web/native client (used on the button)
         *
         * @param fontColour the font colour
         * @return the builder
         */
        public Builder withFontColour(String fontColour) {
            this.fontColour = fontColour;
            return this;
        }

        /**
         * Sets the language locale used by the web/native client
         *
         * @param locale the locale, e.g. "en"
         * @return the builder
         */
        public Builder withLocale(String locale) {
            this.locale = locale;
            return this;
        }

        /**
         * Sets the preset issuing country used by the web/native client
         *
         * @param presetIssuingCountry the preset issuing country, 3 letter ISO code e.g. GBR
         * @return the builder
         */
        public Builder withPresetIssuingCountry(String presetIssuingCountry) {
            this.presetIssuingCountry = presetIssuingCountry;
            return this;
        }

        /**
         * Sets the success URL for the redirect that follows the web/native client uploading documents successfully
         *
         * @param successUrl the success URL
         * @return the builder
         */
        public Builder withSuccessUrl(String successUrl) {
            this.successUrl = successUrl;
            return this;
        }

        /**
         * Sets the error URL for the redirect that follows the web/native client uploading documents unsuccessfully
         *
         * @param errorUrl the error URL
         * @return the builder
         */
        public Builder withErrorUrl(String errorUrl) {
            this.errorUrl = errorUrl;
            return this;
        }

        /**
         * Sets the privacy policy URL
         *
         * @param privacyPolicyUrl the privacy policy URL
         * @return the builder
         */
        public Builder withPrivacyPolicyUrl(String privacyPolicyUrl) {
            this.privacyPolicyUrl = privacyPolicyUrl;
            return this;
        }

        /**
         * Sets if the user is allowed to perform mobile handoff
         *
         * @param allowHandoff if mobile handoff is allowed
         * @return the builder
         */
        public Builder withAllowHandoff(boolean allowHandoff) {
            this.allowHandoff = allowHandoff;
            return this;
        }

        /**
         * Sets the {@link AttemptsConfiguration} for the session
         *
         * @param attemptsConfiguration the configuration for retries
         * @return the builder
         */
        public Builder withAttemptsConfiguration(AttemptsConfiguration attemptsConfiguration) {
            this.attemptsConfiguration = attemptsConfiguration;
            return this;
        }

        /**
         * Builds the {@link SdkConfig} using the values supplied to the builder
         *
         * @return the {@link SdkConfig}
         */
        public SdkConfig build() {
            return new SdkConfig(
                    allowedCaptureMethods,
                    primaryColour,
                    secondaryColour,
                    fontColour,
                    locale,
                    presetIssuingCountry,
                    successUrl,
                    errorUrl,
                    privacyPolicyUrl,
                    allowHandoff,
                    attemptsConfiguration
            );
        }
    }

    private static final class Property {

        private static final String ALLOWED_CAPTURE_METHODS = "allowed_capture_methods";
        private static final String PRIMARY_COLOUR = "primary_colour";
        private static final String SECONDARY_COLOUR = "secondary_colour";
        private static final String FONT_COLOUR = "font_colour";
        private static final String LOCALE = "locale";
        private static final String PRESET_ISSUING_COUNTRY = "preset_issuing_country";
        private static final String SUCCESS_URL = "success_url";
        private static final String ERROR_URL = "error_url";
        private static final String PRIVACY_POLICY_URL = "privacy_policy_url";
        private static final String ALLOW_HANDOFF = "allow_handoff";
        private static final String ATTEMPTS_CONFIGURATION = "attempts_configuration";

        private Property() {}

    }

}

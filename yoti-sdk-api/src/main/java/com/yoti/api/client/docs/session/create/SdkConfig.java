package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides configuration properties used by the web/native clients
 */
public class SdkConfig {

    @JsonProperty("allowed_capture_methods")
    private final String allowedCaptureMethods;

    @JsonProperty("primary_colour")
    private final String primaryColour;

    @JsonProperty("secondary_colour")
    private final String secondaryColour;

    @JsonProperty("font_colour")
    private final String fontColour;

    @JsonProperty("locale")
    private final String locale;

    @JsonProperty("preset_issuing_country")
    private final String presetIssuingCountry;

    @JsonProperty("success_url")
    private final String successUrl;

    @JsonProperty("error_url")
    private final String errorUrl;

    @JsonProperty("privacy_policy_url")
    private final String privacyPolicyUrl;

    SdkConfig(String allowedCaptureMethods,
            String primaryColour,
            String secondaryColour,
            String fontColour,
            String locale,
            String presetIssuingCountry,
            String successUrl,
            String errorUrl,
            String privacyPolicyUrl) {
        this.allowedCaptureMethods = allowedCaptureMethods;
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        this.fontColour = fontColour;
        this.locale = locale;
        this.presetIssuingCountry = presetIssuingCountry;
        this.successUrl = successUrl;
        this.errorUrl = errorUrl;
        this.privacyPolicyUrl = privacyPolicyUrl;
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

        private Builder() {
        }

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
                    privacyPolicyUrl
            );
        }
    }

}

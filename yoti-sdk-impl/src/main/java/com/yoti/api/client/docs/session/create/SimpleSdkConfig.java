package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleSdkConfig implements SdkConfig {

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

    SimpleSdkConfig(String allowedCaptureMethods,
                    String primaryColour,
                    String secondaryColour,
                    String fontColour,
                    String locale,
                    String presetIssuingCountry, String successUrl, String errorUrl) {
        this.allowedCaptureMethods = allowedCaptureMethods;
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        this.fontColour = fontColour;
        this.locale = locale;
        this.presetIssuingCountry = presetIssuingCountry;
        this.successUrl = successUrl;
        this.errorUrl = errorUrl;
    }

    @Override
    public String getAllowedCaptureMethods() {
        return allowedCaptureMethods;
    }

    @Override
    public String getPrimaryColour() {
        return primaryColour;
    }

    @Override
    public String getSecondaryColour() {
        return secondaryColour;
    }

    @Override
    public String getFontColour() {
        return fontColour;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public String getPresetIssuingCountry() {
        return presetIssuingCountry;
    }

    @Override
    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    public String getErrorUrl() {
        return errorUrl;
    }

}

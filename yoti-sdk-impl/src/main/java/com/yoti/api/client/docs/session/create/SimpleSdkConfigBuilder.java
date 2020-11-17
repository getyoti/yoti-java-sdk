package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleSdkConfigBuilder implements SdkConfigBuilder {

    private String allowedCaptureMethods;
    private String primaryColour;
    private String secondaryColour;
    private String fontColour;
    private String locale;
    private String presetIssuingCountry;
    private String successUrl;
    private String errorUrl;
    private String privacyPolicyUrl;

    @Override
    public SdkConfigBuilder withAllowsCamera() {
        return withAllowedCaptureMethods(DocScanConstants.CAMERA);
    }

    @Override
    public SdkConfigBuilder withAllowsCameraAndUpload() {
        return withAllowedCaptureMethods(DocScanConstants.CAMERA_AND_UPLOAD);
    }

    @Override
    public SdkConfigBuilder withAllowedCaptureMethods(String allowedCaptureMethods) {
        this.allowedCaptureMethods = allowedCaptureMethods;
        return this;
    }

    @Override
    public SdkConfigBuilder withPrimaryColour(String primaryColour) {
        this.primaryColour = primaryColour;
        return this;
    }

    @Override
    public SdkConfigBuilder withSecondaryColour(String secondaryColour) {
        this.secondaryColour = secondaryColour;
        return this;
    }

    @Override
    public SdkConfigBuilder withFontColour(String fontColour) {
        this.fontColour = fontColour;
        return this;
    }

    @Override
    public SdkConfigBuilder withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public SdkConfigBuilder withPresetIssuingCountry(String presetIssuingCountry) {
        this.presetIssuingCountry = presetIssuingCountry;
        return this;
    }

    @Override
    public SdkConfigBuilder withSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
        return this;
    }

    @Override
    public SdkConfigBuilder withErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
        return this;
    }

    @Override
    public SdkConfigBuilder withPrivacyPolicyUrl(String privacyPolicyUrl) {
        this.privacyPolicyUrl = privacyPolicyUrl;
        return this;
    }

    @Override
    public SdkConfig build() {
        return new SimpleSdkConfig(allowedCaptureMethods, primaryColour, secondaryColour, fontColour, locale, presetIssuingCountry, successUrl, errorUrl, privacyPolicyUrl);
    }
}

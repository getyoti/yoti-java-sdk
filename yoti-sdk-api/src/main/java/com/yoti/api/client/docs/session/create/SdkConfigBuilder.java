package com.yoti.api.client.docs.session.create;

/**
 * Builder to assist in the creation of {@link SdkConfig}.
 */
public interface SdkConfigBuilder {

    /**
     * Sets the allowed capture method to "CAMERA"
     *
     * @return the builder
     */
    SdkConfigBuilder withAllowsCamera();

    /**
     * Sets the allowed capture method to "CAMERA_AND_UPLOAD"
     *
     * @return the builder
     */
    SdkConfigBuilder withAllowsCameraAndUpload();

    /**
     * Sets the allowed capture method
     *
     * @param allowedCaptureMethod the allowed capture method
     * @return the builder
     */
    SdkConfigBuilder withAllowedCaptureMethod(String allowedCaptureMethod);

    /**
     * Sets the primary colour to be used by the web/native client
     *
     * @param primaryColour the primary colour, hexadecimal value e.g. #ff0000
     * @return the builder
     */
    SdkConfigBuilder withPrimaryColour(String primaryColour);

    /**
     * Sets the secondary colour to be used by the web/native client (used on the button)
     *
     * @param secondaryColour the secondary colour, hexadecimal value e.g. #ff0000
     * @return the builder
     */
    SdkConfigBuilder withSecondaryColour(String secondaryColour);

    /**
     * Sets the font colour to be used by the web/native client (used on the button)
     *
     * @param fontColour the font colour
     * @return the builder
     */
    SdkConfigBuilder withFontColour(String fontColour);

    /**
     * Sets the language locale used by the web/native client
     *
     * @param locale the locale, e.g. "en"
     * @return the builder
     */
    SdkConfigBuilder withLocale(String locale);

    /**
     * Sets the preset issuing country used by the web/native client
     *
     * @param presetIssuingCountry the preset issuing country, 3 letter ISO code e.g. GBR
     * @return the builder
     */
    SdkConfigBuilder withPresetIssuingCountry(String presetIssuingCountry);

    /**
     * Sets the success URL for the redirect that follows the web/native client uploading documents successfully
     *
     * @param successUrl the success URL
     * @return the builder
     */
    SdkConfigBuilder withSuccessUrl(String successUrl);

    /**
     * Sets the error URL for the redirect that follows the web/native client uploading documents unsuccessfully
     *
     * @param errorUrl the error URL
     * @return the builder
     */
    SdkConfigBuilder withErrorUrl(String errorUrl);

    /**
     * Builds the {@link SdkConfig} using the values supplied to the builder
     *
     * @return the {@link SdkConfig}
     */
    SdkConfig build();

}

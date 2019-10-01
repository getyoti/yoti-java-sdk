package com.yoti.api.client.docs.session.create;

/**
 * Provides configuration properties used by the web/native clients
 */
public interface SdkConfig {

    /**
     * The methods allowed for capturing document images
     *
     * @return the allowed capture methods
     */
    String getAllowedCaptureMethods();

    /**
     * The primary colour
     *
     * @return the primary colour
     */
    String getPrimaryColour();

    /**
     * The secondary colour
     *
     * @return the secondary colour
     */
    String getSecondaryColour();

    /**
     * The font colour
     *
     * @return the font colour
     */
    String getFontColour();

    /**
     * The locale
     *
     * @return the locale
     */
    String getLocale();

    /**
     * The preset issuing country
     *
     * @return the preset issuing country
     */
    String getPresetIssuingCountry();

    /**
     * The success URL
     *
     * @return the success URL
     */
    String getSuccessUrl();

    /**
     * The error URL
     *
     * @return the error URL
     */
    String getErrorUrl();

}

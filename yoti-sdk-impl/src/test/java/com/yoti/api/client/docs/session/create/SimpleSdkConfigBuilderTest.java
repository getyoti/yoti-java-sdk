package com.yoti.api.client.docs.session.create;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleSdkConfigBuilderTest {

    private static final String SOME_PRIMARY_COLOUR = "#FFFFFF";
    private static final String SOME_SECONDARY_COLOUR = "#679bdd";
    private static final String SOME_FONT_COLOUR = "#b40c12";
    private static final String SOME_LOCALE = "en";
    private static final String SOME_PRESET_ISSUING_COUNTRY = "USA";

    private static final String SOME_SUCCESS_URL = "https://yourdomain.com/some/success/endpoint";
    private static final String SOME_ERROR_URL = "https://yourdomain.com/some/error/endpoint";
    private static final String SOME_PRIVACY_POLICY_URL = "https://yourdomain.com/some/privacy/policy";

    @Test
    public void shouldBuildSimpleSdkConfigWithAllOptions() {
        SdkConfig result = new SimpleSdkConfigBuilder()
                .withAllowsCamera()
                .withPrimaryColour(SOME_PRIMARY_COLOUR)
                .withSecondaryColour(SOME_SECONDARY_COLOUR)
                .withFontColour(SOME_FONT_COLOUR)
                .withLocale(SOME_LOCALE)
                .withPresetIssuingCountry(SOME_PRESET_ISSUING_COUNTRY)
                .withSuccessUrl(SOME_SUCCESS_URL)
                .withErrorUrl(SOME_ERROR_URL)
                .withPrivacyPolicyUrl(SOME_PRIVACY_POLICY_URL)
                .build();

        assertThat(result, is(instanceOf(SimpleSdkConfig.class)));

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
        assertThat(result.getPrimaryColour(), is(SOME_PRIMARY_COLOUR));
        assertThat(result.getSecondaryColour(), is(SOME_SECONDARY_COLOUR));
        assertThat(result.getFontColour(), is(SOME_FONT_COLOUR));
        assertThat(result.getLocale(), is(SOME_LOCALE));
        assertThat(result.getPresetIssuingCountry(), is(SOME_PRESET_ISSUING_COUNTRY));
        assertThat(result.getSuccessUrl(), is(SOME_SUCCESS_URL));
        assertThat(result.getErrorUrl(), is(SOME_ERROR_URL));
        assertThat(result.getPrivacyPolicyUrl(), is(SOME_PRIVACY_POLICY_URL));
    }

    @Test
    public void shouldBuildSimpleSdkConfigWithOnlyCamera() {
        SdkConfig result = new SimpleSdkConfigBuilder()
                .withAllowsCamera()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
    }

    @Test
    public void shouldBuildSimpleSdkConfigWithCameraAndUpload() {
        SdkConfig result = new SimpleSdkConfigBuilder()
                .withAllowsCameraAndUpload()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA_AND_UPLOAD"));
    }

    @Test
    public void shouldOverridePreviousAllowedCaptureMethods() {
        SdkConfig result = new SimpleSdkConfigBuilder()
                .withAllowsCameraAndUpload()
                .withAllowsCamera()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
    }


}

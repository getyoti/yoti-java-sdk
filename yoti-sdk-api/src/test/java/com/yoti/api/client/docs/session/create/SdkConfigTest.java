package com.yoti.api.client.docs.session.create;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SdkConfigTest {

    private static final String SOME_PRIMARY_COLOUR = "#FFFFFF";
    private static final String SOME_SECONDARY_COLOUR = "#679bdd";
    private static final String SOME_FONT_COLOUR = "#b40c12";
    private static final String SOME_LOCALE = "en";
    private static final String SOME_PRESET_ISSUING_COUNTRY = "USA";
    private static final String SOME_BRAND_ID = "someBrandId";

    private static final String SOME_SUCCESS_URL = "https://yourdomain.com/some/success/endpoint";
    private static final String SOME_ERROR_URL = "https://yourdomain.com/some/error/endpoint";
    private static final String SOME_PRIVACY_POLICY_URL = "https://yourdomain.com/some/privacy/policy";

    @Mock AttemptsConfiguration attemptsConfigurationMock;

    @Test
    public void shouldBuildSimpleSdkConfigWithAllOptions() {
        SdkConfig result = SdkConfig.builder()
                .withAllowsCamera()
                .withPrimaryColour(SOME_PRIMARY_COLOUR)
                .withSecondaryColour(SOME_SECONDARY_COLOUR)
                .withFontColour(SOME_FONT_COLOUR)
                .withLocale(SOME_LOCALE)
                .withPresetIssuingCountry(SOME_PRESET_ISSUING_COUNTRY)
                .withSuccessUrl(SOME_SUCCESS_URL)
                .withErrorUrl(SOME_ERROR_URL)
                .withPrivacyPolicyUrl(SOME_PRIVACY_POLICY_URL)
                .withBrandId(SOME_BRAND_ID)
                .withAllowHandoff(true)
                .withAttemptsConfiguration(attemptsConfigurationMock)
                .withBrandId(SOME_BRAND_ID)
                .build();

        assertThat(result, is(instanceOf(SdkConfig.class)));

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
        assertThat(result.getPrimaryColour(), is(SOME_PRIMARY_COLOUR));
        assertThat(result.getSecondaryColour(), is(SOME_SECONDARY_COLOUR));
        assertThat(result.getFontColour(), is(SOME_FONT_COLOUR));
        assertThat(result.getLocale(), is(SOME_LOCALE));
        assertThat(result.getPresetIssuingCountry(), is(SOME_PRESET_ISSUING_COUNTRY));
        assertThat(result.getSuccessUrl(), is(SOME_SUCCESS_URL));
        assertThat(result.getErrorUrl(), is(SOME_ERROR_URL));
        assertThat(result.getPrivacyPolicyUrl(), is(SOME_PRIVACY_POLICY_URL));
        assertThat(result.getBrandId(), is(SOME_BRAND_ID));
        assertThat(result.getAllowHandoff(), is(true));
        assertThat(result.getAttemptsConfiguration(), is(attemptsConfigurationMock));
        assertThat(result.getBrandId(), is(SOME_BRAND_ID));
    }

    @Test
    public void allowHandoff_shouldBeNullWhenNotSet() {
        SdkConfig result = SdkConfig.builder()
                .build();

        assertThat(result.getAllowHandoff(), is(nullValue()));
    }

    @Test
    public void shouldBuildSimpleSdkConfigWithOnlyCamera() {
        SdkConfig result = SdkConfig.builder()
                .withAllowsCamera()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
    }

    @Test
    public void shouldBuildSimpleSdkConfigWithCameraAndUpload() {
        SdkConfig result = SdkConfig.builder()
                .withAllowsCameraAndUpload()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA_AND_UPLOAD"));
    }

    @Test
    public void shouldOverridePreviousAllowedCaptureMethods() {
        SdkConfig result = SdkConfig.builder()
                .withAllowsCameraAndUpload()
                .withAllowsCamera()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
    }


}

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
    private static final String SOME_PRIMARY_COLOUR_DARK_MODE = "#3b706f";
    private static final String SOME_SECONDARY_COLOUR = "#679bdd";
    private static final String SOME_FONT_COLOUR = "#b40c12";
    private static final String SOME_LOCALE = "en";
    private static final String SOME_DARK_MODE = "ON";
    private static final String SOME_PRESET_ISSUING_COUNTRY = "USA";
    private static final String SOME_BRAND_ID = "someBrandId";
    private static final String SOME_SUPPRESSED_SCREEN = "someSuppressedScreen";
    private static final String SOME_OTHER_SUPPRESSED_SCREEN = "someOtherSuppressedScreen";

    private static final String SOME_SUCCESS_URL = "https://yourdomain.com/some/success/endpoint";
    private static final String SOME_ERROR_URL = "https://yourdomain.com/some/error/endpoint";
    private static final String SOME_PRIVACY_POLICY_URL = "https://yourdomain.com/some/privacy/policy";

    @Mock AttemptsConfiguration attemptsConfigurationMock;

    @Test
    public void shouldBuildSimpleSdkConfigWithAllOptions() {
        SdkConfig result = SdkConfig.builder()
                .withAllowsCamera()
                .withPrimaryColour(SOME_PRIMARY_COLOUR)
                .withPrimaryColourDarkMode(SOME_PRIMARY_COLOUR_DARK_MODE)
                .withSecondaryColour(SOME_SECONDARY_COLOUR)
                .withFontColour(SOME_FONT_COLOUR)
                .withLocale(SOME_LOCALE)
                .withDarkMode(SOME_DARK_MODE)
                .withPresetIssuingCountry(SOME_PRESET_ISSUING_COUNTRY)
                .withSuccessUrl(SOME_SUCCESS_URL)
                .withErrorUrl(SOME_ERROR_URL)
                .withPrivacyPolicyUrl(SOME_PRIVACY_POLICY_URL)
                .withAllowHandoff(true)
                .withAttemptsConfiguration(attemptsConfigurationMock)
                .withBrandId(SOME_BRAND_ID)
                .withSuppressedScreen(SOME_SUPPRESSED_SCREEN)
                .withSuppressedScreen(SOME_OTHER_SUPPRESSED_SCREEN)
                .build();

        assertThat(result, is(instanceOf(SdkConfig.class)));

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
        assertThat(result.getPrimaryColour(), is(SOME_PRIMARY_COLOUR));
        assertThat(result.getPrimaryColourDarkMode(), is(SOME_PRIMARY_COLOUR_DARK_MODE));
        assertThat(result.getSecondaryColour(), is(SOME_SECONDARY_COLOUR));
        assertThat(result.getFontColour(), is(SOME_FONT_COLOUR));
        assertThat(result.getLocale(), is(SOME_LOCALE));
        assertThat(result.getDarkMode(), is(SOME_DARK_MODE));
        assertThat(result.getPresetIssuingCountry(), is(SOME_PRESET_ISSUING_COUNTRY));
        assertThat(result.getSuccessUrl(), is(SOME_SUCCESS_URL));
        assertThat(result.getErrorUrl(), is(SOME_ERROR_URL));
        assertThat(result.getPrivacyPolicyUrl(), is(SOME_PRIVACY_POLICY_URL));
        assertThat(result.getAllowHandoff(), is(true));
        assertThat(result.getAttemptsConfiguration(), is(attemptsConfigurationMock));
        assertThat(result.getBrandId(), is(SOME_BRAND_ID));
        assertThat(result.getSuppressedScreens(), hasSize(2));
        assertThat(result.getSuppressedScreens(), hasItems(SOME_SUPPRESSED_SCREEN, SOME_OTHER_SUPPRESSED_SCREEN));
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
    public void shouldBuildSimpleSdkConfigWithBiometricConsentFlowEarly() {
        SdkConfig result = SdkConfig.builder()
                .withBiometricConsentFlowEarly()
                .build();

        assertThat(result.getBiometricConsentFlow(), is("EARLY"));
    }

    @Test
    public void shouldBuildSimpleSdkConfigWithBiometricConsentFlowJustInTime() {
        SdkConfig result = SdkConfig.builder()
                .withBiometricConsentFlowJustInTime()
                .build();

        assertThat(result.getBiometricConsentFlow(), is("JUST_IN_TIME"));
    }

    @Test
    public void shouldOverridePreviousAllowedCaptureMethods() {
        SdkConfig result = SdkConfig.builder()
                .withAllowsCameraAndUpload()
                .withAllowsCamera()
                .build();

        assertThat(result.getAllowedCaptureMethods(), is("CAMERA"));
    }

    @Test
    public void shouldSetDarkModeToOn() {
        SdkConfig result = SdkConfig.builder()
                .withDarkModeOn()
                .build();

        assertThat(result.getDarkMode(), is("ON"));
    }

    @Test
    public void shouldSetDarkModeToOff() {
        SdkConfig result = SdkConfig.builder()
                .withDarkModeOff()
                .build();

        assertThat(result.getDarkMode(), is("OFF"));
    }

    @Test
    public void shouldSetDarkModeToAuto() {
        SdkConfig result = SdkConfig.builder()
                .withDarkModeAuto()
                .build();

        assertThat(result.getDarkMode(), is("AUTO"));
    }

    @Test
    public void suppressedScreens_shouldBeNullIfNotSet() {
        SdkConfig result = SdkConfig.builder()
                .build();

        assertThat(result.getSuppressedScreens(), is(nullValue()));
    }

}

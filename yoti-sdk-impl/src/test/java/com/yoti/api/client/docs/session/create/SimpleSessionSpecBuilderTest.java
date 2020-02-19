package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.check.SimpleRequestedCheckBuilderFactory;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;
import com.yoti.api.client.docs.session.create.task.SimpleRequestedTaskBuilderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSessionSpecBuilderTest {

    private static final Integer SOME_CLIENT_SESSION_TOKEN_TTL = 300;
    private static final Integer SOME_RESOURCES_TTL = 86000;
    private static final String SOME_USER_TRACKING_ID = "someUserTrackingId";

    private static final String SOME_NOTIFICATION_AUTH_TOKEN = "someAuthToken:someAuthPassword";
    private static final String SOME_NOTIFICATION_ENDPOINT = "https://yourdomain.com/some/notification/endpoint";

    private static final String SOME_SDK_CONFIG_PRIMARY_COLOUR = "#FFFFFF";
    private static final String SOME_SDK_CONFIG_SECONDARY_COLOUR = "#679bdd";
    private static final String SOME_SDK_CONFIG_FONT_COLOUR = "#b40c12";
    private static final String SOME_SDK_CONFIG_LOCALE = "en";
    private static final String SOME_SDK_CONFIG_PRESET_ISSUING_COUNTRY = "USA";

    private static final String SOME_SDK_CONFIG_SUCCESS_URL = "https://yourdomain.com/some/success/endpoint";
    private static final String SOME_SDK_CONFIG_ERROR_URL = "https://yourdomain.com/some/error/endpoint";

    @Mock RequiredDocument requiredDocumentMock;

    @Test
    public void shouldBuildWithMinimalConfiguration() {
        SessionSpec result = new SimpleSessionSpecBuilder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .build();

        assertThat(result, is(instanceOf(SimpleSessionSpec.class)));
        assertThat(result.getClientSessionTokenTtl(), is(SOME_CLIENT_SESSION_TOKEN_TTL));
        assertThat(result.getResourcesTtl(), is(SOME_RESOURCES_TTL));
        assertThat(result.getUserTrackingId(), is(SOME_USER_TRACKING_ID));
        assertThat(result.getRequiredDocuments(), hasSize(0));
    }

    @Test
    public void shouldRaiseForMissingClientSessionTokenTtl() {
        try {
            new SimpleSessionSpecBuilder().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("clientSessionTokenTtl"));
        }
    }

    @Test
    public void shouldRaiseForMissingResourcesTtl() {
        try {
            new SimpleSessionSpecBuilder()
                    .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("resourcesTtl"));
        }
    }

    @Test
    public void shouldRaiseForMissingUserTrackingId() {
        try {
            new SimpleSessionSpecBuilder()
                    .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                    .withResourcesTtl(SOME_RESOURCES_TTL)
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("userTrackingId"));
        }
    }

    @Test
    public void shouldBuildWithValidNotifications() {
        NotificationConfig notificationConfig = new SimpleNotificationConfigBuilder()
                .forResourceUpdate()
                .forSessionCompletion()
                .withAuthToken(SOME_NOTIFICATION_AUTH_TOKEN)
                .withEndpoint(SOME_NOTIFICATION_ENDPOINT)
                .build();

        SessionSpec result = new SimpleSessionSpecBuilder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withNotifications(notificationConfig)
                .build();

        assertThat(result.getNotifications(), is(notNullValue()));
        assertThat(result.getNotifications().getTopics(), hasItems("RESOURCE_UPDATE", "SESSION_COMPLETION"));
        assertThat(result.getNotifications().getAuthToken(), is(SOME_NOTIFICATION_AUTH_TOKEN));
        assertThat(result.getNotifications().getEndpoint(), is(SOME_NOTIFICATION_ENDPOINT));
    }

    @Test
    public void shouldBuildWithValidRequestedChecks() {
        RequestedCheck authenticityCheck = new SimpleRequestedCheckBuilderFactory()
                .forDocumentAuthenticityCheck()
                .build();

        RequestedCheck livenessCheck = new SimpleRequestedCheckBuilderFactory()
                .forLivenessCheck()
                .forZoomLiveness()
                .withMaxRetries(3)
                .build();

        SessionSpec result = new SimpleSessionSpecBuilder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withRequestedCheck(authenticityCheck)
                .withRequestedCheck(livenessCheck)
                .build();

        assertThat(result.getRequestedChecks(), hasSize(2));
        assertThat(result.getRequestedChecks().get(0).getType(), is("ID_DOCUMENT_AUTHENTICITY"));
        assertThat(result.getRequestedChecks().get(1).getType(), is("LIVENESS"));
    }

    @Test
    public void shouldBuildWithValidRequestedTasks() {
        RequestedTask textExtractionTask = new SimpleRequestedTaskBuilderFactory()
                .forTextExtractionTask()
                .withManualCheckAlways()
                .build();

        SessionSpec result = new SimpleSessionSpecBuilder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withRequestedTask(textExtractionTask)
                .build();

        assertThat(result.getRequestedTasks(), hasSize(1));
        assertThat(result.getRequestedTasks().get(0).getType(), is("ID_DOCUMENT_TEXT_DATA_EXTRACTION"));
    }

    @Test
    public void shouldBuildWithValidSdkConfig() {
        SdkConfig sdkConfig = new SimpleSdkConfigBuilder()
                .withAllowsCameraAndUpload()
                .withPrimaryColour(SOME_SDK_CONFIG_PRIMARY_COLOUR)
                .withSecondaryColour(SOME_SDK_CONFIG_SECONDARY_COLOUR)
                .withFontColour(SOME_SDK_CONFIG_FONT_COLOUR)
                .withLocale(SOME_SDK_CONFIG_LOCALE)
                .withPresetIssuingCountry(SOME_SDK_CONFIG_PRESET_ISSUING_COUNTRY)
                .withSuccessUrl(SOME_SDK_CONFIG_SUCCESS_URL)
                .withErrorUrl(SOME_SDK_CONFIG_ERROR_URL)
                .build();

        SessionSpec result = new SimpleSessionSpecBuilder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withSdkConfig(sdkConfig)
                .build();

        assertThat(result.getSdkConfig(), is(notNullValue()));
        assertThat(result.getSdkConfig().getAllowedCaptureMethods(), is("CAMERA_AND_UPLOAD"));
        assertThat(result.getSdkConfig().getPrimaryColour(), is(SOME_SDK_CONFIG_PRIMARY_COLOUR));
        assertThat(result.getSdkConfig().getSecondaryColour(), is(SOME_SDK_CONFIG_SECONDARY_COLOUR));
        assertThat(result.getSdkConfig().getFontColour(), is(SOME_SDK_CONFIG_FONT_COLOUR));
        assertThat(result.getSdkConfig().getLocale(), is(SOME_SDK_CONFIG_LOCALE));
        assertThat(result.getSdkConfig().getPresetIssuingCountry(), is(SOME_SDK_CONFIG_PRESET_ISSUING_COUNTRY));
        assertThat(result.getSdkConfig().getSuccessUrl(), is(SOME_SDK_CONFIG_SUCCESS_URL));
        assertThat(result.getSdkConfig().getErrorUrl(), is(SOME_SDK_CONFIG_ERROR_URL));
    }

    @Test
    public void withRequiredDocument_shouldAddRequiredDocumentToList() {
        SessionSpec result = new SimpleSessionSpecBuilder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withRequiredDocument(requiredDocumentMock)
                .build();

        assertThat(result, is(instanceOf(SimpleSessionSpec.class)));
        assertThat(result.getClientSessionTokenTtl(), is(SOME_CLIENT_SESSION_TOKEN_TTL));
        assertThat(result.getResourcesTtl(), is(SOME_RESOURCES_TTL));
        assertThat(result.getUserTrackingId(), is(SOME_USER_TRACKING_ID));

        assertThat(result.getRequiredDocuments(), hasSize(1));
        assertThat(result.getRequiredDocuments(), contains(requiredDocumentMock));
    }

}

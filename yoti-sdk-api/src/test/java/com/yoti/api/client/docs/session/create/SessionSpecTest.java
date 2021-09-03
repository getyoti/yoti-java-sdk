package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import com.yoti.api.client.docs.session.create.check.RequestedDocumentAuthenticityCheck;
import com.yoti.api.client.docs.session.create.check.RequestedLivenessCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedIdDocTextExtractionTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SessionSpecTest {

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
    @Mock IbvOptions ibvOptionsMock;
    @Mock ZonedDateTime sessionDeadlineMock;

    @Test
    public void shouldBuildWithMinimalConfiguration() {
        SessionSpec result = SessionSpec.builder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withBlockBiometricConsent(true)
                .build();

        assertThat(result, is(instanceOf(SessionSpec.class)));
        assertThat(result.getClientSessionTokenTtl(), is(SOME_CLIENT_SESSION_TOKEN_TTL));
        assertThat(result.getResourcesTtl(), is(SOME_RESOURCES_TTL));
        assertThat(result.getUserTrackingId(), is(SOME_USER_TRACKING_ID));
        assertThat(result.getBlockBiometricConsent(), is(true));
        assertThat(result.getRequiredDocuments(), hasSize(0));
    }

    @Test
    public void shouldRaiseForMissingClientSessionTokenTtl() {
        try {
            SessionSpec.builder().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("clientSessionTokenTtl"));
        }
    }

    @Test
    public void shouldRaiseForMissingResourcesTtl() {
        try {
            SessionSpec.builder()
                    .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("resourcesTtl"));
        }
    }

    @Test
    public void shouldRaiseForMissingUserTrackingId() {
        try {
            SessionSpec.builder()
                    .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                    .withResourcesTtl(SOME_RESOURCES_TTL)
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("userTrackingId"));
        }
    }

    @Test
    public void shouldBuildWithValidNotifications() {
        NotificationConfig notificationConfig = NotificationConfig.builder()
                .forResourceUpdate()
                .forSessionCompletion()
                .withAuthToken(SOME_NOTIFICATION_AUTH_TOKEN)
                .withEndpoint(SOME_NOTIFICATION_ENDPOINT)
                .build();

        SessionSpec result = SessionSpec.builder()
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
        RequestedDocumentAuthenticityCheck authenticityCheck = RequestedDocumentAuthenticityCheck.builder()
                .build();

        RequestedLivenessCheck livenessCheck = RequestedLivenessCheck.builder()
                .forZoomLiveness()
                .withMaxRetries(3)
                .build();

        SessionSpec result = SessionSpec.builder()
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
        RequestedIdDocTextExtractionTask textExtractionTask = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckAlways()
                .build();

        SessionSpec result = SessionSpec.builder()
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
        SdkConfig sdkConfig = SdkConfig.builder()
                .withAllowsCameraAndUpload()
                .withPrimaryColour(SOME_SDK_CONFIG_PRIMARY_COLOUR)
                .withSecondaryColour(SOME_SDK_CONFIG_SECONDARY_COLOUR)
                .withFontColour(SOME_SDK_CONFIG_FONT_COLOUR)
                .withLocale(SOME_SDK_CONFIG_LOCALE)
                .withPresetIssuingCountry(SOME_SDK_CONFIG_PRESET_ISSUING_COUNTRY)
                .withSuccessUrl(SOME_SDK_CONFIG_SUCCESS_URL)
                .withErrorUrl(SOME_SDK_CONFIG_ERROR_URL)
                .build();

        SessionSpec result = SessionSpec.builder()
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
        SessionSpec result = SessionSpec.builder()
                .withClientSessionTokenTtl(SOME_CLIENT_SESSION_TOKEN_TTL)
                .withResourcesTtl(SOME_RESOURCES_TTL)
                .withUserTrackingId(SOME_USER_TRACKING_ID)
                .withRequiredDocument(requiredDocumentMock)
                .build();

        assertThat(result, is(instanceOf(SessionSpec.class)));
        assertThat(result.getClientSessionTokenTtl(), is(SOME_CLIENT_SESSION_TOKEN_TTL));
        assertThat(result.getResourcesTtl(), is(SOME_RESOURCES_TTL));
        assertThat(result.getUserTrackingId(), is(SOME_USER_TRACKING_ID));

        assertThat(result.getRequiredDocuments(), hasSize(1));
        assertThat(result.getRequiredDocuments(), contains(requiredDocumentMock));
    }

    @Test
    public void withIbvOptions_shouldSetTheIbvOptions() {
        SessionSpec result = SessionSpec.builder()
                .withIbvOptions(ibvOptionsMock)
                .build();

        assertThat(result.getIbvOptions(), is(ibvOptionsMock));
    }

    @Test
    public void withSessionDeadline_shouldSetTheSessionDeadline() {
        SessionSpec result = SessionSpec.builder()
                .withSessionDeadline(sessionDeadlineMock)
                .build();

        assertThat(result.getSessionDeadline(), is(sessionDeadlineMock));
    }

}

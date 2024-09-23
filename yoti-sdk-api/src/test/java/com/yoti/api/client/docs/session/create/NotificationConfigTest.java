package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class NotificationConfigTest {

    private static final String SOME_USERNAME = "someUsername";
    private static final String SOME_PASSWORD = "somePassword";
    private static final String SOME_AUTH_TOKEN = SOME_USERNAME + ":" + SOME_PASSWORD;

    private static final String SOME_ENDPOINT = "https://yourdomain,com/some/endpoint";
    private static final String SOME_TOPIC = "someTopic";

    @Test
    public void shouldCreateSimpleNotificationConfigWithAllOptions() {
        NotificationConfig result = NotificationConfig.builder()
                .forCheckCompletion()
                .withAuthToken(SOME_AUTH_TOKEN)
                .withEndpoint(SOME_ENDPOINT)
                .build();

        assertThat(result, is(instanceOf(NotificationConfig.class)));

        assertThat(result.getTopics(), hasSize(1));
        assertThat(result.getTopics(), hasItems("CHECK_COMPLETION"));

        assertThat(result.getAuthToken(), equalTo(SOME_USERNAME + ":" + SOME_PASSWORD));
        assertThat(result.getEndpoint(), equalTo(SOME_ENDPOINT));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithCheckCompletion() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forCheckCompletion()
                .build();

        assertThat(result.getEndpoint(), is(SOME_ENDPOINT));
        assertThat(result.getTopics(), hasItem("CHECK_COMPLETION"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithResourceUpdate() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forResourceUpdate()
                .build();

        assertThat(result.getEndpoint(), is(SOME_ENDPOINT));
        assertThat(result.getTopics(), hasItem("RESOURCE_UPDATE"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithTaskCompletion() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forResourceUpdate()
                .build();

        assertThat(result.getTopics(), hasItem("RESOURCE_UPDATE"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithNewPdfSupplied() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forNewPdfSupplied()
                .build();

        assertThat(result.getTopics(), hasItem("NEW_PDF_SUPPLIED"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithInstructionsEmailRequested() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forInstructionsEmailRequested()
                .build();

        assertThat(result.getTopics(), hasItem("INSTRUCTIONS_EMAIL_REQUESTED"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithThankYouEmailRequested() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forThankYouEmailRequested()
                .build();

        assertThat(result.getTopics(), hasItem("THANK_YOU_EMAIL_REQUESTED"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithFirstBranchVisit() {
        NotificationConfig result = NotificationConfig.builder()
                .withEndpoint(SOME_ENDPOINT)
                .forFirstBranchVisit()
                .build();

        assertThat(result.getTopics(), hasItem("FIRST_BRANCH_VISIT"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithAllNotificationOptions() {
        NotificationConfig result = NotificationConfig.builder()
                .forResourceUpdate()
                .forTaskCompletion()
                .forCheckCompletion()
                .forSessionCompletion()
                .forClientSessionCompletion()
                .withEndpoint(SOME_ENDPOINT)
                .withTopic(SOME_TOPIC)
                .build();

        assertThat(result.getTopics(), hasItems(
                "RESOURCE_UPDATE",
                "SESSION_COMPLETION",
                "CHECK_COMPLETION",
                "SESSION_COMPLETION",
                "CLIENT_SESSION_TOKEN_DELETED"
        ));
    }

    @Test
    public void shouldNotImplicitlySetAValueForAuthType() {
        NotificationConfig result = NotificationConfig.builder()
                .forResourceUpdate()
                .build();

        assertThat(result.getAuthType(), is(nullValue()));
    }

    @Test
    public void shouldSetAuthTypeToBasic() {
        NotificationConfig result = NotificationConfig.builder()
                .forResourceUpdate()
                .withAuthTypeBasic()
                .build();

        assertThat(result.getAuthType(), is("BASIC"));
    }

    @Test
    public void shouldSetAuthTypeToBearer() {
        NotificationConfig result = NotificationConfig.builder()
                .forResourceUpdate()
                .withAuthTypeBearer()
                .build();

        assertThat(result.getAuthType(), is("BEARER"));
    }

}

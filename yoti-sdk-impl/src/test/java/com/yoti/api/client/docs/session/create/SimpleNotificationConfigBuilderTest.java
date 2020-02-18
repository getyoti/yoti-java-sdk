package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class SimpleNotificationConfigBuilderTest {

    private static final String SOME_USERNAME = "someUsername";
    private static final String SOME_PASSWORD = "somePassword";
    private static final String SOME_AUTH_TOKEN = SOME_USERNAME + ":" + SOME_PASSWORD;

    private static final String SOME_ENDPOINT = "https://yourdomain,com/some/endpoint";
    private static final String SOME_TOPIC = "someTopic";

    @Test
    public void shouldCreateSimpleNotificationConfigWithAllOptions() {
        NotificationConfig result = new SimpleNotificationConfigBuilder()
                .forCheckCompletion()
                .withAuthToken(SOME_AUTH_TOKEN)
                .withEndpoint(SOME_ENDPOINT)
                .build();

        assertThat(result, is(instanceOf(SimpleNotificationConfig.class)));

        assertThat(result.getTopics(), hasSize(1));
        assertThat(result.getTopics(), hasItems("CHECK_COMPLETION"));

        assertThat(result.getAuthToken(), equalTo(SOME_USERNAME + ":" + SOME_PASSWORD));
        assertThat(result.getEndpoint(), equalTo(SOME_ENDPOINT));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithCheckCompletion() {
        NotificationConfig result = new SimpleNotificationConfigBuilder()
                .withEndpoint(SOME_ENDPOINT)
                .forCheckCompletion()
                .build();

        assertThat(result.getEndpoint(), is(SOME_ENDPOINT));
        assertThat(result.getTopics(), hasItem("CHECK_COMPLETION"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithResourceUpdate() {
        NotificationConfig result = new SimpleNotificationConfigBuilder()
                .withEndpoint(SOME_ENDPOINT)
                .forResourceUpdate()
                .build();

        assertThat(result.getEndpoint(), is(SOME_ENDPOINT));
        assertThat(result.getTopics(), hasItem("RESOURCE_UPDATE"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithTaskCompletion() {
        NotificationConfig result = new SimpleNotificationConfigBuilder()
                .withEndpoint(SOME_ENDPOINT)
                .forResourceUpdate()
                .build();

        assertThat(result.getTopics(), hasItem("RESOURCE_UPDATE"));
    }

    @Test
    public void shouldCreateSimpleNotificationConfigWithAllNotificationOptions() {
        NotificationConfig result = new SimpleNotificationConfigBuilder()
                .forResourceUpdate()
                .forTaskCompletion()
                .forCheckCompletion()
                .forSessionCompletion()
                .withEndpoint(SOME_ENDPOINT)
                .withTopic(SOME_TOPIC)
                .build();

        assertThat(result.getTopics(), hasItems(
                "RESOURCE_UPDATE",
                "SESSION_COMPLETION",
                "CHECK_COMPLETION",
                "SESSION_COMPLETION"
        ));
    }

}

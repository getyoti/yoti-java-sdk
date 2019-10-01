package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleNotificationConfigBuilderFactoryTest {

    private final NotificationConfigBuilderFactory testObj = new SimpleNotificationConfigBuilderFactory();

    @Test
    public void shouldReturnSimpleNotificationConfigBuilder() {
        NotificationConfigBuilder result = testObj.create();

        assertThat(result, is(instanceOf(SimpleNotificationConfigBuilder.class)));
    }

}

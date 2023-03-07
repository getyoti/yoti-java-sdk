package com.yoti.api.spring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class ClientPropertiesTest {

    private static final String SCENARIO_ID = "scenario-id-123";
    private static final String CLIENT_SDK_ID = "abc-123";
    private static final String ACCESS_SECURITY_KEY = "classpath:/my-key.pem";
    private static final String YOTI_PREFIX = "com.yoti.client";

    @Test
    public void scenarioIdShouldBeSet() {
        ClientProperties properties = new ClientProperties();
        properties.setScenarioId(SCENARIO_ID);
        assertThat(properties.getScenarioId(), is(SCENARIO_ID));
    }

    @Test
    public void clientSdkIdShouldBeSet() {
        ClientProperties properties = new ClientProperties();
        properties.setClientSdkId(CLIENT_SDK_ID);
        assertThat(properties.getClientSdkId(), is(CLIENT_SDK_ID));
    }

    @Test
    public void accessSecurityKeyShouldBeSet() {
        ClientProperties properties = new ClientProperties();
        properties.setAccessSecurityKey(ACCESS_SECURITY_KEY);
        assertThat(properties.getAccessSecurityKey(), is(ACCESS_SECURITY_KEY));
    }

    @Test
    public void defaultValuesShouldBeNull() {
        ClientProperties properties = new ClientProperties();
        assertThat(properties.getAccessSecurityKey(), nullValue());
        assertThat(properties.getClientSdkId(), nullValue());
        assertThat(properties.getScenarioId(), nullValue());
    }

    @Test
    public void configurationPrefixShouldBeAsExpected() {
        final ConfigurationProperties annotation = ClientProperties.class.getAnnotation(ConfigurationProperties.class);
        assertThat(annotation.prefix(), is(YOTI_PREFIX));
    }

}

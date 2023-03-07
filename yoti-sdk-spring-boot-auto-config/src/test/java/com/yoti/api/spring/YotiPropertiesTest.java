package com.yoti.api.spring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class YotiPropertiesTest {

    private static final String APPLICATION_ID = "abc-123";
    private static final String SCENARIO_ID = "123-abc";
    private static final String YOTI_PREFIX = "com.yoti";

    @Test
    public void applicationIdShouldBeSet() {
        final YotiProperties properties = new YotiProperties();
        properties.setApplicationId(APPLICATION_ID);
        assertThat(properties.getApplicationId(), is(APPLICATION_ID));
    }

    @Test
    public void scenarioIdShouldBeSet() {
        final YotiProperties properties = new YotiProperties();
        properties.setScenarioId(SCENARIO_ID);
        assertThat(properties.getScenarioId(), is(SCENARIO_ID));
    }

    @Test
    public void defaultValuesShouldBeNull() {
        final YotiProperties properties = new YotiProperties();
        assertThat(properties.getApplicationId(), nullValue());
        assertThat(properties.getScenarioId(), nullValue());
    }

    /**
     * Just here as a guard against someone changing the prefix as this would cause any existing client applications to break.
     */
    @Test
    public void configurationPrefixShouldBeAsExpected() {
        final ConfigurationProperties annotation = YotiProperties.class.getAnnotation(ConfigurationProperties.class);
        assertThat(annotation.prefix(), is(YOTI_PREFIX));
    }

}

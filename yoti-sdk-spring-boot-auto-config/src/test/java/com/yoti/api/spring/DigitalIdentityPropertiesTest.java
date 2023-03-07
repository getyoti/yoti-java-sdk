package com.yoti.api.spring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class DigitalIdentityPropertiesTest {
    
    private static final String APPLICATION_ID = "anAppId";
    private static final String IDENTITY_PREFIX = "com.yoti.identity";

    @Test
    public void applicationIdShouldBeSet() {
        DigitalIdentityProperties properties = new DigitalIdentityProperties();
        properties.setApplicationId(APPLICATION_ID);
        assertThat(properties.getApplicationId(), is(APPLICATION_ID));
    }

    @Test
    public void defaultValuesShouldBeNull() {
        DigitalIdentityProperties properties = new DigitalIdentityProperties();
        assertThat(properties.getApplicationId(), nullValue());
    }

    @Test
    public void configurationPrefixShouldBeAsExpected() {
        ConfigurationProperties annotation = DigitalIdentityProperties.class.getAnnotation(ConfigurationProperties.class);
        assertThat(annotation.prefix(), is(IDENTITY_PREFIX));
    }
    
}

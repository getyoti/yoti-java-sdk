package com.yoti.api.spring;

import org.junit.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class YotiClientPropertiesTest {

    private static final String CLIENT_SDK_ID = "abc-123";
    private static final String ACCESS_SECURITY_KEY = "classpath:/my-key.pem";
    private static final String YOTI_PREFIX = "com.yoti.client";

    @Test
    public void clientSdkIdShouldBeSet() throws Exception {
        final YotiClientProperties properties = new YotiClientProperties();
        properties.setClientSdkId(CLIENT_SDK_ID);
        assertThat(properties.getClientSdkId(), is(CLIENT_SDK_ID));
    }

    @Test
    public void accessSecurityKeyShouldBeSet() throws Exception {
        final YotiClientProperties properties = new YotiClientProperties();
        properties.setAccessSecurityKey(ACCESS_SECURITY_KEY);
        assertThat(properties.getAccessSecurityKey(), is(ACCESS_SECURITY_KEY));
    }

    @Test
    public void defaultValuesShouldBeNull() throws Exception {
        final YotiClientProperties properties = new YotiClientProperties();
        assertThat(properties.getAccessSecurityKey(), nullValue());
        assertThat(properties.getClientSdkId(), nullValue());
    }

    /**
     * Just here as a guard against someone changing the prefix as this would cause any existing client applications to break.
     */
    @Test
    public void configurationPrefixShouldBeAsExpected() throws Exception {
        final ConfigurationProperties annotation = YotiClientProperties.class.getAnnotation(ConfigurationProperties.class);
        assertThat(annotation.prefix(), is(YOTI_PREFIX));
    }
}

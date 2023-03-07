package com.yoti.api.spring;

import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.YotiClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Automatically configures a Yoti Client instance based on the {@link ClientProperties}
 * provided as long as a bean of the same type doesn't already exist.
 */
@Configuration
@ConditionalOnClass(YotiClient.class)
@EnableConfigurationProperties({ ClientProperties.class, YotiProperties.class})
public class YotiClientAutoConfiguration {

    private final ClientProperties properties;
    private final ResourceLoader resourceLoader;

    @Autowired
    public YotiClientAutoConfiguration(ClientProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Configures a Yoti client if a bean of this type does not already exist.
     *
     * @param keyPairSource the instance of a Key Pair Source configured separately as another bean.
     * @return the configured client.
     */
    @Bean
    @ConditionalOnMissingBean(YotiClient.class)
    public YotiClient yotiClient(KeyPairSource keyPairSource) {
        return YotiClient.builder()
                .withClientSdkId(properties.getClientSdkId())
                .withKeyPair(keyPairSource)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(KeyPairSource.class)
    public KeyPairSource keyPairSource() {
        return loadAsSpringResource(properties);
    }

    private KeyPairSource loadAsSpringResource(ClientProperties properties) {
        final Resource keyPairResource = resourceLoader.getResource(properties.getAccessSecurityKey());
        return new SpringResourceKeyPairSource(keyPairResource);
    }

}

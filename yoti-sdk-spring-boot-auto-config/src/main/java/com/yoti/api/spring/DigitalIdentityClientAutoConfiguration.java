package com.yoti.api.spring;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.KeyPairSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Automatically configures a Digital Identity Client instance based on the {@link ClientProperties}
 * provided as long as a bean of the same type doesn't already exist.
 */
@Configuration
@ConditionalOnClass(DigitalIdentityClient.class)
@EnableConfigurationProperties({ ClientProperties.class, DigitalIdentityProperties.class })
public class DigitalIdentityClientAutoConfiguration {

    private final ClientProperties properties;
    private final ResourceLoader resourceLoader;

    @Autowired
    public DigitalIdentityClientAutoConfiguration(ClientProperties properties, ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Configures a Digital Identity Client if a bean of this type does not already exist.
     *
     * @param keyPairSource the instance of a Key Pair Source configured separately as another bean.
     * @return the configured client.
     */
    @Bean
    @ConditionalOnMissingBean(DigitalIdentityClient.class)
    public DigitalIdentityClient digitalIdentityClient(KeyPairSource keyPairSource)  {
        return DigitalIdentityClient.builder()
                .withClientSdkId(properties.getClientSdkId())
                .withKeyPairSource(keyPairSource)
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

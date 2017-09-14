package com.yoti.api.spring;

import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.YotiClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Automatically configures a Yoti Client instance based on the {@link YotiClientProperties}
 * provided as long as a bean of the same type doesn't already exist.
 */
@Configuration
@ConditionalOnClass(YotiClient.class)
@EnableConfigurationProperties({YotiClientProperties.class, YotiProperties.class})
public class YotiClientAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringResourceKeyPairSource.class);

    @Autowired
    private YotiClientProperties properties;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Configures a Yoti client if a bean of this type does not already exist.
     *
     * @param keyPairSource the instance of a key pair source configured separately as another bean.
     * @return the configured client.
     * @throws Exception if the client could not be created.
     */
    @Bean
    @ConditionalOnMissingBean(YotiClient.class)
    public YotiClient yotiClient(final KeyPairSource keyPairSource) throws Exception {
        LOGGER.info("Configuring Yoti client with {} and {}.", properties, keyPairSource);
        return YotiClientBuilder.newInstance()
                .forApplication(properties.getClientSdkId())
                .withKeyPair(keyPairSource)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(KeyPairSource.class)
    public KeyPairSource keyPairSource() {
        LOGGER.info("Configuring key pair source based on {}.", properties);
        return loadAsSpringResource(properties);
    }

    private KeyPairSource loadAsSpringResource(final YotiClientProperties properties) {
        final Resource keyPairResource = resourceLoader.getResource(properties.getAccessSecurityKey());
        return new SpringResourceKeyPairSource(keyPairResource);
    }
}

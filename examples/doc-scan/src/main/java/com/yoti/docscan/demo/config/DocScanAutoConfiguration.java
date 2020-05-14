package com.yoti.docscan.demo.config;

import com.yoti.api.client.ClassPathKeySource;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.docs.DocScanClient;
import com.yoti.api.client.docs.DocScanClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@ConditionalOnClass(DocScanClient.class)
@EnableConfigurationProperties({ DocScanConfig.class })
public class DocScanAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocScanAutoConfiguration.class);

    private DocScanConfig properties;
    private ResourceLoader resourceLoader;

    @Autowired
    public DocScanAutoConfiguration(DocScanConfig properties,
            ResourceLoader resourceLoader) {
        this.properties = properties;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    @ConditionalOnMissingBean(DocScanClient.class)
    public DocScanClient getDocScanClient(@Qualifier("docScanPem") final KeyPairSource keyPairSource) {
        LOGGER.info("Configuring Doc Scan client with {} and {}.", properties, keyPairSource);
        return DocScanClientBuilder.newInstance()
                .withClientSdkId(properties.getSdkId())
                .withKeyPairSource(keyPairSource)
                .build();
    }

    @Bean(name = "docScanPem")
    public KeyPairSource getDocScanKeyPairSource() {
        return ClassPathKeySource.fromClasspath(properties.getPemFileLocation());
    }

}

package com.yoti.docscan.demo.config;

import com.yoti.api.client.ClassPathKeySource;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.docs.DocScanClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DocScanClient.class)
@EnableConfigurationProperties({ DocScanConfig.class })
public class DocScanAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocScanAutoConfiguration.class);

    private final DocScanConfig properties;

    @Autowired
    public DocScanAutoConfiguration(DocScanConfig properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(DocScanClient.class)
    public DocScanClient getDocScanClient(@Qualifier("docScanPem") final KeyPairSource keyPairSource) {
        LOGGER.info("Configuring Doc Scan client with {} and {}.", properties, keyPairSource);
        return DocScanClient.builder()
                .withClientSdkId(properties.getSdkId())
                .withKeyPairSource(keyPairSource)
                .build();
    }

    @Bean(name = "docScanPem")
    public KeyPairSource getDocScanKeyPairSource() {
        return ClassPathKeySource.fromClasspath(properties.getPemFileLocation());
    }

}

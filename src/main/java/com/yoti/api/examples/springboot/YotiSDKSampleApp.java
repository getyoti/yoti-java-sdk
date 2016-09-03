package com.yoti.api.examples.springboot;

import static com.yoti.api.client.ClassPathKeySource.fromClasspath;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.YotiClientBuilder;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
public class YotiSDKSampleApp {

    @Value("${yotiApp.id}")
    private String applicationId;

    public static void main(String[] args) {
        SpringApplication.run(YotiSDKSampleApp.class, args);
    }

    public @Bean YotiClient getYotiClient() throws IOException, InitialisationException {
        return YotiClientBuilder.newInstance()
                .forApplication(applicationId) //Yoti SDK Application Id
                .withKeyPair(fromClasspath("app-keypair.pem"))
                .build();
    }

    @Bean
    public PropertySource<?> yamlPropertySourceLoader() throws IOException {
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        PropertySource<?> applicationYamlPropertySource = loader.load("application.yml",
                new ClassPathResource("application.yml"), "default");
        return applicationYamlPropertySource;
    }

}

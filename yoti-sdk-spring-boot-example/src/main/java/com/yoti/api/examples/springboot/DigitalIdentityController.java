package com.yoti.api.examples.springboot;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.spring.ClientProperties;
import com.yoti.api.spring.DigitalIdentityProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass(DigitalIdentityClient.class)
@EnableConfigurationProperties({ ClientProperties.class, DigitalIdentityProperties.class })
@Controller
@EnableWebMvc
@RequestMapping("/v2")
public class DigitalIdentityController implements WebMvcConfigurer {

    private final DigitalIdentityClient client;
    private final ClientProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Autowired
    public DigitalIdentityController(DigitalIdentityClient client, ClientProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @RequestMapping("/")
    public String home(final Model model) {
        model.addAttribute("clientSdkId", properties.getClientSdkId());
        model.addAttribute("scenarioId", properties.getScenarioId());
        return "index";
    }

    @RequestMapping("/digital-identity-share")
    public String identityShare(final Model model) {
        model.addAttribute("message", "Example page for identity share using Yoti web-share");

        return "digital-identity-share";
    }

}

package com.yoti.api.examples.springboot;

import java.net.URI;
import java.net.URISyntaxException;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.identity.policy.Policy;
import com.yoti.api.spring.ClientProperties;
import com.yoti.api.spring.DigitalIdentityProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(DigitalIdentityController.class);

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
    public String home(Model model) {
        model.addAttribute("clientSdkId", properties.getClientSdkId());
        model.addAttribute("scenarioId", properties.getScenarioId());
        return "index";
    }

    @RequestMapping("/digital-identity-share")
    public String identityShare(Model model) throws URISyntaxException {
        model.addAttribute("message", "Example page for identity share using Yoti web-share");

        Policy policy = Policy.builder().build();

        ShareSessionRequest shareSessionRequest = ShareSessionRequest.builder()
                .withPolicy(policy)
                .withRedirectUri(new URI("https://host/redirect/"))
                .build();

        ShareSession result = null;
        try {
            result = client.createShareSession(shareSessionRequest);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }

        model.addAttribute("message", "Identity creation example");

        model.addAttribute("sdkId", properties.getClientSdkId());

        model.addAttribute("session_id", result.getId());
        model.addAttribute("session_status", result.getStatus());
        model.addAttribute("session_expiry", result.getExpiry());

        return "digital-identity-share";
    }

}

package com.yoti.api.examples.springboot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionQrCode;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.identity.policy.Policy;
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
    public String home(Model model) {
        model.addAttribute("clientSdkId", properties.getClientSdkId());
        model.addAttribute("scenarioId", properties.getScenarioId());
        return "index";
    }

    @RequestMapping("/digital-identity-share")
    public String identityShare(Model model) throws URISyntaxException {
        model.addAttribute("sdkId", properties.getClientSdkId());
        model.addAttribute("message", "Example page for identity share");

        Policy policy = Policy.builder().build();

        ShareSessionRequest shareSessionRequest = ShareSessionRequest.builder()
                .withPolicy(policy)
                .withRedirectUri(new URI("https://host/redirect/"))
                .build();

        ShareSession session = execute(() -> client.createShareSession(shareSessionRequest), model);
        if (session == null) {
            return "error";
        }

        String sessionId = session.getId();

        model.addAttribute("session_id", sessionId);
        model.addAttribute("session_status", session.getStatus());
        model.addAttribute("session_expiry", session.getExpiry());

        ShareSessionQrCode sessionQrCode = execute(() -> client.createShareQrCode(sessionId), model);
        if (sessionQrCode == null) {
            return "error";
        }

        String qrCodeId = sessionQrCode.getId();

        model.addAttribute("session_qrcode_id", qrCodeId);
        model.addAttribute("session_qrcode_uri", sessionQrCode.getUri());

        ShareSessionQrCode fetchQrCode = execute(() -> client.fetchShareQrCode(qrCodeId), model);
        if (fetchQrCode == null) {
            return "error";
        }

        model.addAttribute("qrcode_expiry", fetchQrCode.getExpiry());
        model.addAttribute("qrcode_extensions", fetchQrCode.getExtensions());
        model.addAttribute("qrcode_redirect_uri", fetchQrCode.getRedirectUri());
        model.addAttribute("qrcode_session_id", fetchQrCode.getSession().getId());
        model.addAttribute("qrcode_session_status", fetchQrCode.getSession().getStatus());
        model.addAttribute("qrcode_session_expiry", fetchQrCode.getSession().getExpiry());

        return "digital-identity-share";
    }

    private static <T> T execute(Supplier<T> supplier, Model model) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return null;
        }
    }

}

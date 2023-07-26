package com.yoti.api.examples.springboot;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.spi.remote.call.identity.Receipt;
import com.yoti.api.examples.springboot.attribute.AttributeMapper;
import com.yoti.api.examples.springboot.attribute.DisplayAttribute;
import com.yoti.api.spring.ClientProperties;
import com.yoti.api.spring.DigitalIdentityProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass(DigitalIdentityClient.class)
@EnableConfigurationProperties({ ClientProperties.class, DigitalIdentityProperties.class })
@Controller
@EnableWebMvc
@RequestMapping("/v2")
public class IdentityLoginController implements WebMvcConfigurer {

    private final DigitalIdentityClient client;
    private final ClientProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Autowired
    public IdentityLoginController(DigitalIdentityClient client, ClientProperties properties) {
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
    public String identityShare(Model model) {
        model.addAttribute("sdkId", properties.getClientSdkId());
        model.addAttribute("message", "Example page for identity share");

        return "digital-identity-share";
    }

    @GetMapping(value = "/receipt")
    public String receipt(@RequestParam("receiptId") String receiptId, Model model) {
        Receipt receipt = execute(() -> client.fetchShareReceipt(receiptId), model);

        if (receipt == null || receipt.getError().isPresent()) {
            model.addAttribute("error", receipt.getError().get());
            return "error";
        }

        Receipt.ApplicationContent applicationContent = receipt.getApplicationContent();

        Optional.ofNullable(applicationContent.getProfile())
                .map(ApplicationProfile::getApplicationLogo)
                .map(attr -> model.addAttribute("appLogo", attr.getValue().getBase64Content()));

        receipt.getProfile().map(HumanProfile::getSelfie)
                .map(attr -> model.addAttribute("base64Selfie", attr.getValue().getBase64Content()));
        receipt.getProfile().map(HumanProfile::getFullName)
                .map(attr -> model.addAttribute("fullName", attr.getValue()));
        receipt.getProfile().map(HumanProfile::getAttributes)
                .map(attr -> model.addAttribute("displayAttributes", mapAttributes(attr)));

        return "profile";
    }

    private List<DisplayAttribute> mapAttributes(Collection<Attribute<?>> attributes) {
        return attributes.stream()
                .map(AttributeMapper::mapToDisplayAttribute)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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

package com.yoti.api.examples.springboot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Image;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.shareurl.DynamicScenario;
import com.yoti.api.client.shareurl.DynamicShareException;
import com.yoti.api.client.shareurl.ShareUrlResult;
import com.yoti.api.client.shareurl.extension.Extension;
import com.yoti.api.client.shareurl.extension.LocationConstraintContent;
import com.yoti.api.client.shareurl.extension.LocationConstraintExtensionBuilder;
import com.yoti.api.client.shareurl.policy.DynamicPolicy;
import com.yoti.api.client.shareurl.policy.WantedAttribute;
import com.yoti.api.examples.springboot.attribute.AttributeMapper;
import com.yoti.api.examples.springboot.attribute.DisplayAttribute;
import com.yoti.api.spring.ClientProperties;
import com.yoti.api.spring.YotiProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnClass(YotiClient.class)
@EnableConfigurationProperties({ ClientProperties.class, YotiProperties.class })
@Controller
@EnableWebMvc
public class YotiLoginController implements WebMvcConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(YotiLoginController.class);

    private final YotiClient client;
    private final ClientProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Autowired
    public YotiLoginController(final YotiClient client, ClientProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @RequestMapping("/")
    public String home(final Model model) {
        model.addAttribute("clientSdkId", properties.getClientSdkId());
        model.addAttribute("scenarioId", properties.getScenarioId());
        return "index";
    }

    @RequestMapping("/dynamic-share")
    public String dynamicShareHome(final Model model) {
        Extension<LocationConstraintContent> locationExtension = new LocationConstraintExtensionBuilder()
                .withLatitude(51.5074)
                .withLongitude(-0.1278)
                .withRadius(6000)
                .build();

        WantedAttribute givenNamesWantedAttribute = WantedAttribute.builder()
                .withName("given_names")
                .build();

        WantedAttribute emailAddressWantedAttribute = WantedAttribute.builder()
                .withName("email_address")
                .build();

        DynamicPolicy dynamicPolicy = DynamicPolicy.builder()
                .withWantedAttribute(givenNamesWantedAttribute)
                .withWantedAttribute(emailAddressWantedAttribute)
                .withFullName()
                .withSelfie()
                .withPhoneNumber()
                .withAgeOver(18)
                .build();

        DynamicScenario dynamicScenario = DynamicScenario.builder()
                .withCallbackEndpoint("/login")
                .withPolicy(dynamicPolicy)
                .withExtension(locationExtension)
                .build();

        try {
            String shareUrl = client.createShareUrl(dynamicScenario).getUrl();
            model.addAttribute("yotiShareUrl", shareUrl);
        } catch (DynamicShareException e) {
            LOG.error(e.getMessage());
        }

        model.addAttribute("clientSdkId", properties.getClientSdkId());

        return "dynamic-share";
    }

    /**
     * This endpoint is the "Callback URL" which will be called by user's browser after user logs in. It's a GET endpoint.
     * We will pass you a token inside url query string (/login?token=token-value)
     */
    @RequestMapping("/login")
    public String doLogin(@RequestParam("token") final String token, final Model model) {
        ActivityDetails activityDetails;
        HumanProfile humanProfile;

        try {
            activityDetails = client.getActivityDetails(token);
            humanProfile = activityDetails.getUserProfile();
        } catch (final ProfileException profileException) {
            LOG.info("Could not get profile", profileException);
            model.addAttribute("error", profileException.getMessage());
            return "error";
        }

        // load application logo into ui model
        Attribute<Image> applicationLogo = activityDetails.getApplicationProfile().getApplicationLogo();
        if (applicationLogo != null) {
            model.addAttribute("appLogo", applicationLogo.getValue().getBase64Content());
        }

        // load humanProfile data into ui model
        Attribute<Image> selfie = humanProfile.getSelfie();
        if (selfie != null) {
            model.addAttribute("base64Selfie", selfie.getValue().getBase64Content());
        }
        Attribute<String> fullName = humanProfile.getFullName();
        if (fullName != null) {
            model.addAttribute("fullName", fullName.getValue());
        }

        List<DisplayAttribute> displayAttributes = humanProfile.getAttributes().stream()
                .map(AttributeMapper::mapToDisplayAttribute)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        model.addAttribute("displayAttributes", displayAttributes);

        return "profile";
    }

    @RequestMapping("/dbs-check")
    public String dbsCheck(final Model model) {
        Map<String, Object> scheme = new HashMap<>();
        scheme.put("type", "DBS");
        scheme.put("objective", "BASIC");

        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put("trust_framework", "UK_TFIDA");
        identityProfile.put("scheme", scheme);

        DynamicPolicy dynamicPolicy = DynamicPolicy.builder()
                .withIdentityProfile(identityProfile)
                .build();

        Map<String, Object> subject = new HashMap<>();
        subject.put("subject_id", "00000000-1111-2222-3333-444444444444");

        DynamicScenario dynamicScenario = DynamicScenario.builder()
                .withCallbackEndpoint("/login")
                .withPolicy(dynamicPolicy)
                .withSubject(subject)
                .build();

        try {
            ShareUrlResult result = client.createShareUrl(dynamicScenario);

            String shareUrl = result.getUrl();
            model.addAttribute("yotiShareUrl", shareUrl);
        } catch (DynamicShareException e) {
            LOG.error(e.getMessage());
        }

        model.addAttribute("clientSdkId", properties.getClientSdkId());

        return "dbs-check";
    }

}

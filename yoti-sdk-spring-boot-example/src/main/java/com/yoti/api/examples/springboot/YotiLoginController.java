package com.yoti.api.examples.springboot;

import java.util.List;
import java.util.stream.Collectors;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Image;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.spring.YotiClientProperties;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.util.StringUtils;

@Configuration
@ConditionalOnClass(YotiClient.class)
@EnableConfigurationProperties({ YotiClientProperties.class, YotiProperties.class })
@Controller
@EnableWebMvc
public class YotiLoginController extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(YotiLoginController.class);

    private final YotiClient client;
    private final YotiClientProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Autowired
    public YotiLoginController(final YotiClient client, YotiClientProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @RequestMapping("/")
    public String home(final Model model) {
        model.addAttribute("applicationId", properties.getApplicationId());
        return "index";
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
                .map(this::mapToDisplayAttribute)
                .filter(displayAttribute -> displayAttribute != null)
                .collect(Collectors.toList());
        model.addAttribute("displayAttributes", displayAttributes);

        return "profile";
    }

    private DisplayAttribute mapToDisplayAttribute(Attribute attribute) {
        switch (attribute.getName()) {
            case AttributeConstants.HumanProfileAttributes.FULL_NAME:
                return new DisplayAttribute("Full name", attribute, "yoti-icon-profile");
            case AttributeConstants.HumanProfileAttributes.GIVEN_NAMES:
                return new DisplayAttribute("Given names", attribute, "yoti-icon-profile");
            case AttributeConstants.HumanProfileAttributes.FAMILY_NAME:
                return new DisplayAttribute("Family name", attribute, "yoti-icon-profile");
            case AttributeConstants.HumanProfileAttributes.NATIONALITY:
                return new DisplayAttribute("Nationality", attribute, "yoti-icon-nationality");
            case AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS:
                return new DisplayAttribute("Address", attribute, "yoti-icon-address");
            case AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS:
                return null; // Do nothing - we are handling this with the postalAddress attribute
            case AttributeConstants.HumanProfileAttributes.PHONE_NUMBER:
                return new DisplayAttribute("Mobile number", attribute, "yoti-icon-phone");
            case AttributeConstants.HumanProfileAttributes.EMAIL_ADDRESS:
                return new DisplayAttribute("Email address", attribute, "yoti-icon-email");
            case AttributeConstants.HumanProfileAttributes.DATE_OF_BIRTH:
                return new DisplayAttribute("Date of birth", attribute, "yoti-icon-calendar");
            case AttributeConstants.HumanProfileAttributes.SELFIE:
                return null; // Do nothing - we already display the selfie
            case AttributeConstants.HumanProfileAttributes.GENDER:
                return new DisplayAttribute("Gender", attribute, "yoti-icon-gender");

            default:
                if (attribute.getName().contains(":")) {
                    return handleAgeVerification(attribute);
                } else {
                    return handleProfileAttribute(attribute);
                }
        }
    }

    private DisplayAttribute handleAgeVerification(Attribute attribute) {
        return new DisplayAttribute("Age Verification/", "Age verified", attribute, "yoti-icon-verified");
    }

    private DisplayAttribute handleProfileAttribute(Attribute attribute) {
        String attributeName = StringUtils.capitalize(attribute.getName());
        return new DisplayAttribute(attributeName, attribute, "yoti-icon-profile");
    }

}

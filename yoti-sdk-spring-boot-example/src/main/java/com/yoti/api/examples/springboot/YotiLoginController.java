package com.yoti.api.examples.springboot;

import java.util.List;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.Anchor;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.DateTime;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Image;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.spring.YotiClientProperties;
import com.yoti.api.spring.YotiProperties;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
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

@Configuration
@ConditionalOnClass(YotiClient.class)
@EnableConfigurationProperties({ YotiClientProperties.class, YotiProperties.class })
@Controller
public class YotiLoginController {

    private static final Logger LOG = LoggerFactory.getLogger(YotiLoginController.class);

    private final YotiClient client;
    private final YotiClientProperties properties;

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
            return "error";
        }

        // load activityDetails into ui model
        model.addAttribute("rememberMeId", activityDetails.getRememberMeId());

        // load humanProfile data into ui model
        Attribute<Image> selfie = humanProfile.getSelfie();
        model.addAttribute("base64Selfie", selfie == null ? "" : selfie.getValue().getBase64Content());

        addAttributeToModel(model, humanProfile.getFamilyName());
        addAttributeToModel(model, humanProfile.getGivenNames());
        addAttributeToModel(model, humanProfile.getFullName());
        addAttributeToModel(model, humanProfile.getDateOfBirth());
        addAttributeToModel(model, humanProfile.getGender());
        addAttributeToModel(model, humanProfile.getPostalAddress());
        addAttributeToModel(model, humanProfile.getNationality());
        addAttributeToModel(model, humanProfile.getPhoneNumber());
        addAttributeToModel(model, humanProfile.getEmailAddress());

        // load derived properties into ui model
        model.addAttribute("isAgeVerified", humanProfile.isAgeVerified());

        return "profile";
    }

    private void addAttributeToModel(Model model, Attribute attribute) {
        if (attribute != null) {
            String attributeName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, attribute.getName());
            model.addAttribute(attributeName, attribute.getValue());
            model.addAttribute(attributeName + "Sources", attribute.getSources());
            model.addAttribute(attributeName + "Verifiers", attribute.getVerifiers());
        }
    }

}

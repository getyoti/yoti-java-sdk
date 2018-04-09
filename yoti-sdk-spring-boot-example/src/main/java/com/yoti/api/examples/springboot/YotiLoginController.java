package com.yoti.api.examples.springboot;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.HumanProfile;
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

@Configuration
@ConditionalOnClass(YotiClient.class)
@EnableConfigurationProperties({YotiClientProperties.class, YotiProperties.class})
@Controller
public class YotiLoginController {

    private static final Logger LOG = LoggerFactory.getLogger(YotiLoginController.class);

    private final YotiClient client;

    @Autowired
    private YotiClientProperties properties;

    @Autowired
    public YotiLoginController(final YotiClient client) {
        this.client = client;
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
        HumanProfile profile;
        try {
            activityDetails = client.getActivityDetails(token);
            profile = activityDetails.getUserProfile();
        } catch (final ProfileException profileException) {
            LOG.info("Could not get profile", profileException);
            return "error";
        }

        // load profile data into model
        model.addAttribute("fullName", profile.getFullName());
        model.addAttribute("givenNames", profile.getGivenNames());
        model.addAttribute("familyName", profile.getFamilyName());
        model.addAttribute("phoneNumber", profile.getPhoneNumber());
        model.addAttribute("dateOfBirth", profile.getDateOfBirth());
        model.addAttribute("isAgeVerified", profile.isAgeVerified());
        model.addAttribute("emailAddress", profile.getEmailAddress());
        model.addAttribute("postalAddress", profile.getPostalAddress());
        model.addAttribute("nationality", profile.getNationality());
        model.addAttribute("gender", profile.getGender());
        model.addAttribute("userId", activityDetails.getUserId());
        model.addAttribute("base64Selfie", activityDetails.getBase64Selfie());

        return "profile";
    }
}
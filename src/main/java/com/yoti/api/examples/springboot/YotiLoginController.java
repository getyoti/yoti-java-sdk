package com.yoti.api.examples.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;

@Controller
public class YotiLoginController {
    private static final Logger LOG = LoggerFactory.getLogger(YotiLoginController.class);
    @Autowired
    private YotiClient client;

    /**
     * This endpoint is the "Callback URL" which will be called by user's browser after user logs in. It's a GET endpoint.
     * We will pass you a token inside url query string (/login?token=token-value)
     */
    @RequestMapping("/login")
    public String doLogin(@RequestParam("token") String token, Model model) {
        ActivityDetails activityDetails;
        HumanProfile profile;
        try {
            activityDetails = client.getActivityDetails(token);
            profile = activityDetails.getUserProfile();
        } catch (ProfileException e) {
            LOG.info("Could not get profile", e);
            return "error";
        }

        // load profile data into model
        model.addAttribute("name", profile.getGivenNames());
        model.addAttribute("phone", profile.getPhoneNumber());
        model.addAttribute("userId", activityDetails.getUserId());

        return "home";
    }

}

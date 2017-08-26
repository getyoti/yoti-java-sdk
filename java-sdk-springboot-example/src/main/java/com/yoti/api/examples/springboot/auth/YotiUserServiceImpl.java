package com.yoti.api.examples.springboot.auth;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.spring.security.service.YotiUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptySet;

@Service
public class YotiUserServiceImpl implements YotiUserService {

    @Override
    public Authentication login(final ActivityDetails activityDetails) {
        /*
         * It is likely at this point you would check your own user database (perhaps using the rememberMeId as some type of key to identify whether this user has visited you before).
         * You'll probably also want to store any details that you are interested in keeping after the user's session has expired.
         */
        final String rememberMeId = activityDetails.getUserId();
        final HumanProfile profile = activityDetails.getUserProfile();
        final String name = profile.getGivenNames() + " " + profile.getFamilyName();
        final YotiPrincipal authenticatedPrincipal = new YotiPrincipal(name, rememberMeId, emptySet());
        authenticatedPrincipal.setAuthenticated(true);
        return authenticatedPrincipal;
    }
}

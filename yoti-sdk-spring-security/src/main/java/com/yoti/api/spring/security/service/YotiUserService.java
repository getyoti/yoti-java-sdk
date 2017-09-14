package com.yoti.api.spring.security.service;

import com.yoti.api.client.ActivityDetails;
import org.springframework.security.core.Authentication;

/**
 * A service which inspects provided activity details to construct an Authentication object for the user.
 */
public interface YotiUserService {

    /**
     * Returns an authenticated user based on the provided activity details.
     *
     * @param activityDetails the activity details that come from the encrypted Yoti Token.
     * @return the Authenticated Principal.
     */
    Authentication login(ActivityDetails activityDetails);
}

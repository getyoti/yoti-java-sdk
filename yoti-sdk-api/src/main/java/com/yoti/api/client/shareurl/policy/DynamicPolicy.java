package com.yoti.api.client.shareurl.policy;

import java.util.Collection;
import java.util.Set;

/**
 * Set of data required to request a sharing transaction
 */
public interface DynamicPolicy {

    /**
     * Set of required {@link WantedAttribute}
     *
     * @return attributes
     */
    Collection<WantedAttribute> getWantedAttributes();

    /**
     * Type of authentications
     *
     * @return authentication types
     */
    Set<Integer> getWantedAuthTypes();

    /**
     * Allows to remember the {@link DynamicPolicy}
     *
     * @return RememberMe
     */
    boolean isWantedRememberMe();

    /**
     * Defines the {@link #isWantedRememberMe()} optional for the sharing
     *
     * @return RememberMeOptional
     */
    boolean isWantedRememberMeOptional();

}

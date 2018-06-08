package com.yoti.api.client.qrcode.policy;

import java.util.List;

/**
 * Set of data required to request a sharing transaction
 *
 */
public interface Policy {

    /**
     * Set of required {@link Attribute}
     *
     * @return attributes
     */
    List<Attribute> getWantedAttributes();

    /**
     * Type of authentications
     *
     * @return authentication types
     */
    List<Integer> getWantedAuthTypes();

    /**
     * Allows to remember the {@link Policy}
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

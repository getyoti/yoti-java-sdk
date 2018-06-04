package com.yoti.api.client.qrcode.policy;

import java.util.List;

/**
 * Policy
 */
public interface Policy {

    /**
     * getWantedAttributes
     */
    List<Attribute> getWantedAttributes();

    /**
     * getWantedAuthTypes
     */
    List<Integer> getWantedAuthTypes();

    /**
     * isWantedRememberMe
     */
    boolean isWantedRememberMe();

    /**
     * isWantedRememberMeOptional
     */
    boolean isWantedRememberMeOptional();

}

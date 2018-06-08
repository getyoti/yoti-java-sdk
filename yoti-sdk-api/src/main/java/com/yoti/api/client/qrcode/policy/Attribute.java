package com.yoti.api.client.qrcode.policy;

import java.util.List;

/**
 * Type and content of an user detail
 *
 */
public interface Attribute {

    /**
     * Name identifying the {@link Attribute}
     *
     * @return name of the attribute
     */
    String getName();

    /**
     * Links between the attribute and user data sources
     *
     * @return list of anchors
     */
    List<String> getAnchors();

    /**
     * Additional derived criteria
     *
     * @return derivations
     */
    String getDerivation();

    /**
     * Defines the {@link Attribute} as not mandatory
     *
     * @return optional
     */
    boolean isOptional();

}

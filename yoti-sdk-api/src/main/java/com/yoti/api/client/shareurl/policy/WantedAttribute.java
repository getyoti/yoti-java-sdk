package com.yoti.api.client.shareurl.policy;

/**
 * Type and content of an user detail
 *
 */
public interface WantedAttribute {

    /**
     * Name identifying the {@link WantedAttribute}
     *
     * @return name of the attribute
     */
    String getName();

    /**
     * Additional derived criteria
     *
     * @return derivations
     */
    String getDerivation();

    /**
     * Defines the {@link WantedAttribute} as not mandatory
     *
     * @return optional
     */
    boolean isOptional();

}

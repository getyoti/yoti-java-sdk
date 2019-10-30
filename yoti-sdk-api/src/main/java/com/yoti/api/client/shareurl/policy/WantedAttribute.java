package com.yoti.api.client.shareurl.policy;

import com.yoti.api.client.shareurl.constraint.Constraint;

import java.util.List;

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

    /**
     * Allows self asserted attributes
     * @return accept self asserted
     */
    Boolean getAcceptSelfAsserted();

    /**
     * List of {@link Constraint} for a {@link WantedAttribute}
     *
     * @return the list of constrains
     */
    List<Constraint> getConstraints();

}

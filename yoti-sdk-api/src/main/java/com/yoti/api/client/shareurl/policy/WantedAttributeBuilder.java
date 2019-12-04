package com.yoti.api.client.shareurl.policy;

import com.yoti.api.client.shareurl.constraint.Constraint;

import java.util.List;
import java.util.ServiceLoader;

public abstract class WantedAttributeBuilder {

    public static final WantedAttributeBuilder newInstance() {
        ServiceLoader<WantedAttributeBuilder> wantedAttributeBuilderLoader = ServiceLoader.load(WantedAttributeBuilder.class);
        if (!wantedAttributeBuilderLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + WantedAttributeBuilder.class.getSimpleName());
        }
        WantedAttributeBuilder wantedAttributeBuilder = wantedAttributeBuilderLoader.iterator().next();
        return wantedAttributeBuilder.createWantedAttributeBuilder();
    }

    protected abstract WantedAttributeBuilder createWantedAttributeBuilder();

    /**
     * Set the name of the {@link WantedAttribute}
     *
     * @param name the name
     * @return the builder
     */
    public abstract WantedAttributeBuilder withName(String name);

    /**
     * Set the derivation for the {@link WantedAttribute}
     *
     * @param derivation the derivation
     * @return the builder
     */
    public abstract WantedAttributeBuilder withDerivation(String derivation);

    /**
     * Sets the {@link WantedAttribute} to be an optional attribute
     *
     * @param optional
     * @return the builder
     */
    public abstract WantedAttributeBuilder withOptional(boolean optional);

    /**
     * Allow or deny the acceptance of self asserted attributes
     *
     * @param acceptSelfAsserted
     * @return the builder
     */
    public abstract WantedAttributeBuilder withAcceptSelfAsserted(boolean acceptSelfAsserted);

    /**
     * Sets the list of {@link Constraint}
     *
     * @param constraints the list of {@link Constraint}
     * @return the builder
     */
    public abstract WantedAttributeBuilder withConstraints(List<Constraint> constraints);

    /**
     * Add to the list of {@link Constraint}
     *
     * @param constraint the {@link Constraint}
     * @return the builder
     */
    public abstract WantedAttributeBuilder withConstraint(Constraint constraint);

    /**
     * Creates a {@link WantedAttribute} with the values supplied
     *
     * @return the {@link WantedAttribute}
     */
    public abstract WantedAttribute build();

}

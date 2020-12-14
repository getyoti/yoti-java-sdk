package com.yoti.api.client;

import static java.lang.Boolean.parseBoolean;

import static com.yoti.api.client.spi.remote.util.Validation.matchesPattern;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

/**
 * Wraps an 'Age Verify/Condition' attribute to provide behaviour specific to verifying someone's age
 */
public final class AgeVerification {

    private final Attribute<String> derivedAttribute;
    private final int ageVerified;
    private final String checkPerformed;
    private final boolean result;

    public AgeVerification(Attribute<String> derivedAttribute) {
        notNull(derivedAttribute, "derivedAttribute");
        matchesPattern(derivedAttribute.getName(), "[^:]+:(?!.*:)[0-9]+", "attribute.name");
        this.derivedAttribute = derivedAttribute;

        String[] split = derivedAttribute.getName()
                .split(":");
        this.checkPerformed = split[0];
        this.ageVerified = Integer.parseInt(split[1]);
        this.result = parseBoolean(derivedAttribute.getValue());
    }

    /**
     * The age that was that checked, as specified on Yoti Hub.
     *
     * @return The age that was that checked
     */
    public int getAge() {
        return ageVerified;
    }

    /**
     * The type of age check performed, as specified on Yoti Hub. Currently this might be 'age_over' or 'age_under'
     *
     * @return The type of age check performed
     */
    public String getCheckType() {
        return checkPerformed;
    }

    /**
     * Whether or not the profile passed the age check
     *
     * @return The result of the age check.
     */
    public boolean getResult() {
        return result;
    }

    /**
     * The wrapped profile attribute. Use this if you need access to the underlying List of {@link Anchor}s
     *
     * @return The wrapped profile attribute
     */
    public Attribute<String> getAttribute() {
        return derivedAttribute;
    }

}

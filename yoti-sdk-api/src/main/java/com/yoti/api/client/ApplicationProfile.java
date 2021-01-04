package com.yoti.api.client;

import java.util.List;

import com.yoti.api.attributes.AttributeConstants;

public class ApplicationProfile extends Profile {

    /**
     * Create a new profile based on a list of attributes
     *
     * @param attributeList list containing the attributes for this profile
     */
    public ApplicationProfile(List<Attribute<?>> attributeList) {
        super(attributeList);
    }

    /**
     * The name of the application.
     *
     * @return The name of the application.
     */
    public Attribute<String> getApplicationName() {
        return getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_NAME, String.class);
    }

    /**
     * The URI verified by Portal where the application is available at
     *
     * @return The URI verified by Portal where the application is available at
     */
    public Attribute<String> getApplicationUrl() {
        return getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_URL, String.class);
    }

    /**
     * The logo of the application that will be displayed to those users that perform a sharing with it.
     *
     * @return The logo of the application that will be displayed to those users that perform a sharing with it.
     */
    public Attribute<Image> getApplicationLogo() {
        return getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_LOGO, Image.class);
    }

    /**
     * The background colour that will be displayed on each receipt the user gets as a result of a sharing with the
     * application.
     *
     * @return The background colour that will be displayed on each receipt the user gets as a result of a sharing with the
     * application.
     */
    public Attribute<String> getApplicationReceiptBgColor() {
        return getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_RECEIPT_BGCOLOR, String.class);
    }

}

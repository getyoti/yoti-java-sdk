package com.yoti.api.client.shareurl.extension;

import java.util.Date;
import java.util.List;

public interface ThirdPartyAttributeExtensionBuilder extends ExtensionBuilder<ThirdPartyAttributeContent> {

    /**
     * Allows you to specify the expiry date of the third party attribute
     *
     * @param expiryDate the expiry date
     * @return the builder
     */
    ThirdPartyAttributeExtensionBuilder withExpiryDate(Date expiryDate);

    /**
     * Add a definition to the list of specified third party attribute definitions
     *
     * @param definition the definition
     * @return the builder
     */
    ThirdPartyAttributeExtensionBuilder withDefinition(String definition);

    /**
     * Set the list of third party attribute definitions
     *
     * @param definitions the list of definitions
     * @return the builder
     */
    ThirdPartyAttributeExtensionBuilder withDefinitions(List<String> definitions);

}

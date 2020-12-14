package com.yoti.api.client.shareurl.extension;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yoti.api.client.AttributeDefinition;

public class ThirdPartyAttributeExtensionBuilder implements ExtensionBuilder<ThirdPartyAttributeContent> {

    private Date expiryDate;
    private List<AttributeDefinition> definitions;

    public ThirdPartyAttributeExtensionBuilder() {
        this.definitions = new ArrayList<>();
    }

    /**
     * Allows you to specify the expiry date of the third party attribute
     *
     * @param expiryDate the expiry date
     * @return the builder
     */
    public ThirdPartyAttributeExtensionBuilder withExpiryDate(Date expiryDate) {
        notNull(expiryDate, "expiryDate");
        this.expiryDate = expiryDate;
        return this;
    }

    /**
     * Add a definition to the list of specified third party attribute definitions
     *
     * @param definition the definition
     * @return the builder
     */
    public ThirdPartyAttributeExtensionBuilder withDefinition(String definition) {
        notNullOrEmpty(definition, "definition");
        this.definitions.add(new AttributeDefinition(definition));
        return this;
    }

    /**
     * Set the list of third party attribute definitions
     *
     * @param definitions the list of definitions
     * @return the builder
     */
    public ThirdPartyAttributeExtensionBuilder withDefinitions(List<String> definitions) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        for (String definition : definitions) {
            attributeDefinitions.add(new AttributeDefinition(definition));
        }
        this.definitions = attributeDefinitions;
        return this;
    }

    /**
     * @return An object, T, built with parameters of this builder
     */
    public Extension<ThirdPartyAttributeContent> build() {
        ThirdPartyAttributeContent thirdPartyAttributeContent = new ThirdPartyAttributeContent(expiryDate, definitions);
        return new Extension<>(ExtensionConstants.THIRD_PARTY_ATTRIBUTE, thirdPartyAttributeContent);
    }

}

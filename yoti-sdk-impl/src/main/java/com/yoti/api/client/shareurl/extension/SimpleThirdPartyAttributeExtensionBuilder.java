package com.yoti.api.client.shareurl.extension;

import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.spi.remote.SimpleAttributeDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

public class SimpleThirdPartyAttributeExtensionBuilder implements ThirdPartyAttributeExtensionBuilder {

    private Date expiryDate;
    private List<AttributeDefinition> definitions;

    public SimpleThirdPartyAttributeExtensionBuilder() {
        this.definitions = new ArrayList<>();
    }

    /**
     * Allows you to specify the expiry date of the third party attribute
     *
     * @param expiryDate the expiry date
     * @return the builder
     */
    @Override
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
    @Override
    public ThirdPartyAttributeExtensionBuilder withDefinition(String definition) {
        notNullOrEmpty(definition, "definition");
        this.definitions.add(new SimpleAttributeDefinition(definition));
        return this;
    }

    /**
     * Set the list of third party attribute definitions
     *
     * @param definitions the list of definitions
     * @return the builder
     */
    @Override
    public ThirdPartyAttributeExtensionBuilder withDefinitions(List<String> definitions) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        for (String definition : definitions) {
            attributeDefinitions.add(new SimpleAttributeDefinition(definition));
        }
        this.definitions = attributeDefinitions;
        return this;
    }

    /**
     * @return An object, T, built with parameters of this builder
     */
    @Override
    public Extension<ThirdPartyAttributeContent> build() {
        ThirdPartyAttributeContent thirdPartyAttributeContent = new ThirdPartyAttributeContent(expiryDate, definitions);
        return new SimpleExtension(ExtensionConstants.THIRD_PARTY_ATTRIBUTE, thirdPartyAttributeContent);
    }
}

package com.yoti.api.client.identity.extension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.AttributeDefinition;
import com.yoti.validation.Validation;

public class ThirdPartyAttributeExtensionBuilder implements ExtensionBuilder<ThirdPartyAttributeContent> {

    private OffsetDateTime expiryDate;
    private List<AttributeDefinition> definitions;

    public ThirdPartyAttributeExtensionBuilder() {
        this.definitions = new ArrayList<>();
    }

    public ThirdPartyAttributeExtensionBuilder withExpiryDate(OffsetDateTime expiryDate) {
        Validation.notNull(expiryDate, Property.EXPIRY_DATE);

        this.expiryDate = expiryDate;
        return this;
    }

    public ThirdPartyAttributeExtensionBuilder withDefinition(String definition) {
        Validation.notNullOrEmpty(definition, Property.DEFINITION);

        this.definitions.add(new AttributeDefinition(definition));
        return this;
    }

    public ThirdPartyAttributeExtensionBuilder withDefinitions(List<String> definitions) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        for (String definition : definitions) {
            attributeDefinitions.add(new AttributeDefinition(definition));
        }
        this.definitions = attributeDefinitions;
        return this;
    }

    public Extension<ThirdPartyAttributeContent> build() {
        ThirdPartyAttributeContent thirdPartyAttributeContent = new ThirdPartyAttributeContent(expiryDate, definitions);
        return new Extension<>("THIRD_PARTY_ATTRIBUTE", thirdPartyAttributeContent);
    }

    private static final class Property {

        private static final String EXPIRY_DATE = "expiryDate";
        private static final String DEFINITION = "definition";

    }

}

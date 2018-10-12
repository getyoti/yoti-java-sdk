package com.yoti.api.client.spi.remote;

import java.util.Map;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;
import com.yoti.api.client.Attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AddressTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeListConverter.class);

    private AddressTransformer() {}

    static final AddressTransformer newInstance() {
        return new AddressTransformer();
    }

    public Attribute<String> transform(Attribute<?> structuredAddress) {
        Attribute<String> transformedAddress = null;
        try {
            Attribute<Map<?, ?>> address = (Attribute<Map<?, ?>>) structuredAddress;
            if (address.getValue() != null) {
                Object formattedAddress = address.getValue().get(HumanProfileAttributes.Keys.FORMATTED_ADDRESS);
                if (formattedAddress != null) {
                    transformedAddress = new SimpleAttribute(HumanProfileAttributes.POSTAL_ADDRESS,
                            String.valueOf(formattedAddress),
                            structuredAddress.getSources(),
                            structuredAddress.getVerifiers(),
                            structuredAddress.getAnchors());
                }
            }
        } catch (Exception e) {
            LOG.warn("Failed to transform attribute '{}' in place of missing '{}' due to '{}'", structuredAddress.getName(),
                    HumanProfileAttributes.POSTAL_ADDRESS, e.getMessage());
        }
        return transformedAddress;
    }

}

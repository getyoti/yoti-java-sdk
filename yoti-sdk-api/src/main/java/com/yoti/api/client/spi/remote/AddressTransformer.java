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

    Attribute<String> transform(Attribute<?> structuredPostalAddress) {
        Attribute<String> transformedAddress = null;
        try {
            if (structuredPostalAddress.getValue() != null) {
                final Map<?, ?> structuredAddressValue = (Map<?, ?>) structuredPostalAddress.getValue();
                final Object formattedAddress = structuredAddressValue.get(HumanProfileAttributes.Keys.FORMATTED_ADDRESS);
                if (formattedAddress != null) {
                    transformedAddress = new SimpleAttribute(HumanProfileAttributes.POSTAL_ADDRESS,
                            String.valueOf(formattedAddress),
                            structuredPostalAddress.getSources(),
                            structuredPostalAddress.getVerifiers(),
                            structuredPostalAddress.getAnchors());
                }
            }
        } catch (Exception e) {
            LOG.warn("Failed to transform attribute '{}' in place of missing '{}' due to '{}'", structuredPostalAddress.getName(),
                    HumanProfileAttributes.POSTAL_ADDRESS, e.getMessage());
        }
        return transformedAddress;
    }

}

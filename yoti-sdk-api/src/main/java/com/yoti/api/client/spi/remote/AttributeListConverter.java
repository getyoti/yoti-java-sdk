package com.yoti.api.client.spi.remote;

import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.POSTAL_ADDRESS;
import static com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes.STRUCTURED_POSTAL_ADDRESS;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.AttributeListProto;
import com.yoti.api.client.spi.remote.proto.AttributeProto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AttributeListConverter {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeListConverter.class);

    private final AttributeConverter attributeConverter;
    private final AddressTransformer addressTransformer;

    private AttributeListConverter(AttributeConverter attributeConverter, AddressTransformer addressTransformer) {
        this.attributeConverter = attributeConverter;
        this.addressTransformer = addressTransformer;
    }

    static AttributeListConverter newInstance() {
        return new AttributeListConverter(AttributeConverter.newInstance(), AddressTransformer.newInstance());
    }

    List<Attribute<?>> parseAttributeList(byte[] attributeListBytes) throws ProfileException {
        if (attributeListBytes == null || attributeListBytes.length == 0) {
            return Collections.emptyList();
        }

        AttributeListProto.AttributeList attributeList = parseProto(attributeListBytes);
        List<Attribute<?>> attributes = parseAttributes(attributeList);
        LOG.debug("{} out of {} attribute(s) parsed successfully ", attributes.size(), attributeList.getAttributesCount());
        ensurePostalAddress(attributes);
        return attributes;
    }

    private AttributeListProto.AttributeList parseProto(byte[] attributeListBytes) throws ProfileException {
        try {
            return AttributeListProto.AttributeList.parseFrom(attributeListBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new ProfileException("Cannot parse profile data", e);
        }
    }

    private List<Attribute<?>> parseAttributes(AttributeListProto.AttributeList message) {
        List<Attribute<?>> parsedAttributes = new ArrayList<>();
        for (AttributeProto.Attribute attribute : message.getAttributesList()) {
            try {
                parsedAttributes.add(attributeConverter.convertAttribute(attribute));
            } catch (IOException | ParseException e) {
                LOG.warn("Failed to parse attribute '{}' due to '{}'", attribute.getName(), e.getMessage());
            }
        }
        return parsedAttributes;
    }

    private void ensurePostalAddress(List<Attribute<?>> attributes) {
        if (findAttribute(POSTAL_ADDRESS, attributes) == null) {
            Attribute<?> structuredPostalAddress = findAttribute(STRUCTURED_POSTAL_ADDRESS, attributes);
            if (structuredPostalAddress != null) {
                Attribute<String> transformedAddress = addressTransformer.transform(structuredPostalAddress);
                if (transformedAddress != null) {
                    LOG.debug("Substituting '{}' in place of missing '{}'", STRUCTURED_POSTAL_ADDRESS, POSTAL_ADDRESS);
                    attributes.add(transformedAddress);
                }
            }
        }
    }

    private Attribute<?> findAttribute(String name, List<Attribute<?>> attributes) {
        for (Attribute<?> attribute : attributes) {
            if (name.equals(attribute.getName())) {
                return attribute;
            }
        }
        return null;
    }

}

package com.yoti.api.client.spi.remote;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.AttributeListProto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AttributeListConverter {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeListConverter.class);

    private final AttributeConverter attributeConverter;

    private AttributeListConverter(AttributeConverter attributeConverter) {
        this.attributeConverter = attributeConverter;
    }

    static AttributeListConverter newInstance() {
        return new AttributeListConverter(AttributeConverter.newInstance());
    }

    List<Attribute<?>> parseAttributeList(byte[] attributeListBytes) throws ProfileException {
        if (attributeListBytes == null || attributeListBytes.length == 0) {
            return Collections.emptyList();
        }

        AttributeListProto.AttributeList attributeList = parseProto(attributeListBytes);
        List<Attribute<?>> attributes = parseAttributes(attributeList);
        LOG.debug("{} attribute(s) parsed", attributes.size());
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
        for (AttrProto.Attribute attribute : message.getAttributesList()) {
            try {
                parsedAttributes.add(attributeConverter.convertAttribute(attribute));
            } catch (IOException | ParseException e) {
                LOG.warn("Failed to parse attribute '{}'", attribute.getName());
            }
        }
        return parsedAttributes;
    }

}

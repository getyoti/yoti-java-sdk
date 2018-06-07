package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.AttrProto.Anchor;
import com.yoti.api.client.spi.remote.util.AnchorCertificateParser;
import com.yoti.api.client.spi.remote.util.AnchorCertificateParser.AnchorVerifierSourceData;
import com.yoti.api.client.spi.remote.util.AnchorType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeConverter {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(AttributeConverter.class);

    public static Attribute convertAttribute(com.yoti.api.client.spi.remote.proto.AttrProto.Attribute attribute) throws ParseException, IOException {
        switch (attribute.getContentType()) {
            case STRING:
                return attributeWithMetadata(attribute, attribute.getValue().toString(DEFAULT_CHARSET));
            case DATE:
                return attributeWithMetadata(attribute, DateAttributeValue.parseFrom(attribute.getValue().toByteArray()));
            case JPEG:
                return attributeWithMetadata(attribute, new JpegAttributeValue(attribute.getValue().toByteArray()));
            case PNG:
                return attributeWithMetadata(attribute, new PngAttributeValue(attribute.getValue().toByteArray()));
            case JSON:
                return attributeWithMetadata(attribute, JSON_MAPPER.readValue(attribute.getValue().toString(DEFAULT_CHARSET), Map.class));
            default:
                LOG.error("Unknown type {} for attribute {}", attribute.getContentType(), attribute.getName());
                return attributeWithMetadata(attribute, attribute.getValue().toString(DEFAULT_CHARSET));
        }
    }

    private static Attribute attributeWithMetadata(AttrProto.Attribute attribute, Object value) {
        return new Attribute(attribute.getName(), value, extractMetadata(attribute, AnchorType.SOURCE), extractMetadata(attribute, AnchorType.VERIFIER));
    }

    private static Set<String> extractMetadata(AttrProto.Attribute attribute, AnchorType anchorType) {
        Set<String> entries = new HashSet<>();
        for (Anchor anchor : attribute.getAnchorsList()) {
            AnchorVerifierSourceData anchorData = AnchorCertificateParser.getTypesFromAnchor(anchor);
            if (anchorData.getType().equals(anchorType)) {
                entries.addAll(anchorData.getEntries());
            }
        }
        return entries;
    }

}

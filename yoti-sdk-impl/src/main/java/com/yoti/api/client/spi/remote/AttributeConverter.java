package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

class AttributeConverter {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(AttributeConverter.class);

    private final DocumentDetailsAttributeParser documentDetailsAttributeParser;

    private AttributeConverter(DocumentDetailsAttributeParser documentDetailsAttributeParser) {
        this.documentDetailsAttributeParser = documentDetailsAttributeParser;
    }

    static final AttributeConverter newInstance() {
        return new AttributeConverter(new DocumentDetailsAttributeParser());
    }

    <T> Attribute<T> convertAttribute(AttrProto.Attribute attribute) throws ParseException, IOException {
        Object value = convertValueFromProto(attribute);
        value = convertSpecialType(attribute, value);
        Set<String> sources = extractMetadata(attribute, AnchorType.SOURCE);
        Set<String> verifiers = extractMetadata(attribute, AnchorType.VERIFIER);
        return new Attribute(attribute.getName(), value, sources, verifiers);
    }

    private Object convertValueFromProto(AttrProto.Attribute attribute) throws ParseException, IOException {
        switch (attribute.getContentType()) {
            case STRING:
                return attribute.getValue().toString(DEFAULT_CHARSET);
            case DATE:
                return DateValue.parseFrom(attribute.getValue().toByteArray());
            case JPEG:
                return new JpegAttributeValue(attribute.getValue().toByteArray());
            case PNG:
                return new PngAttributeValue(attribute.getValue().toByteArray());
            case JSON:
                return JSON_MAPPER.readValue(attribute.getValue().toString(DEFAULT_CHARSET), Map.class);
            default:
                LOG.error("Unknown type {} for attribute {}", attribute.getContentType(), attribute.getName());
                return attribute.getValue().toString(DEFAULT_CHARSET);
        }
    }

    private Object convertSpecialType(AttrProto.Attribute attribute, Object value) throws UnsupportedEncodingException, ParseException {
        switch (attribute.getName()) {
            case HumanProfileAdapter.ATTRIBUTE_DOCUMENT_DETAILS:
                return documentDetailsAttributeParser.parseFrom((String) value);
            default:
                return value;
        }
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

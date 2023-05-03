package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.Anchor;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.Date;
import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.proto.AttributeProto;
import com.yoti.api.client.spi.remote.util.AnchorType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AttributeConverter {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(AttributeConverter.class);

    private final DocumentDetailsAttributeParser documentDetailsAttributeParser;
    private final AnchorConverter anchorConverter;

    private AttributeConverter(DocumentDetailsAttributeParser documentDetailsAttributeParser, AnchorConverter anchorConverter) {
        this.documentDetailsAttributeParser = documentDetailsAttributeParser;
        this.anchorConverter = anchorConverter;
    }

    static AttributeConverter newInstance() {
        return new AttributeConverter(new DocumentDetailsAttributeParser(), new AnchorConverter());
    }

    <T> Attribute<T> convertAttribute(AttributeProto.Attribute attribute) throws ParseException, IOException {
        Object value = convertValueFromProto(attribute);
        value = convertSpecialType(attribute, value);
        List<Anchor> anchors = convertAnchors(attribute);
        List<Anchor> sources = filterAnchors(anchors, AnchorType.SOURCE.name());
        List<Anchor> verifiers = filterAnchors(anchors, AnchorType.VERIFIER.name());
        return new Attribute(emptyToNull(attribute.getEphemeralId()), attribute.getName(), value, sources, verifiers, anchors);
    }

    private static String emptyToNull(String value) {
        return value == null || value.isEmpty() ? null : value;
    }

    private Object convertValueFromProto(AttributeProto.Attribute attribute) throws ParseException, IOException {
        return convertValue(attribute.getContentType(), attribute.getValue());
    }

    private Object convertValue(AttributeProto.ContentType contentType, ByteString value) throws ParseException, IOException {
        boolean isInvalid = (contentType != AttributeProto.ContentType.STRING && value.isEmpty());
        if (isInvalid) {
            throw new ParseException("Only STRING attributes can have an empty value", 0);
        }

        switch (contentType) {
            case STRING:
                return value.toString(DEFAULT_CHARSET);
            case DATE:
                return Date.parseFrom(value.toByteArray());
            case JPEG:
                return new JpegAttributeValue(value.toByteArray());
            case PNG:
                return new PngAttributeValue(value.toByteArray());
            case JSON:
                return JSON_MAPPER.readValue(value.toString(DEFAULT_CHARSET), Map.class);
            case MULTI_VALUE:
                return convertMultiValue(value);
            case INT:
                return Integer.parseInt(value.toString(DEFAULT_CHARSET));
            default:
                LOG.warn("Unknown type '{}', attempting to parse it as a String", contentType);
                return value.toString(DEFAULT_CHARSET);
        }
    }

    private List<Object> convertMultiValue(ByteString value) throws IOException, ParseException {
        List<Object> list = new ArrayList<>();
        for (AttributeProto.MultiValue.Value thisValue : AttributeProto.MultiValue.parseFrom(value).getValuesList()) {
            Object o = convertValue(thisValue.getContentType(), thisValue.getData());
            list.add(o);
        }
        return list;
    }

    private Object convertSpecialType(AttributeProto.Attribute attribute, Object value) throws UnsupportedEncodingException, ParseException {
        switch (attribute.getName()) {
            case AttributeConstants.HumanProfileAttributes.DOCUMENT_DETAILS:
                return documentDetailsAttributeParser.parseFrom((String) value);
            case AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES:
                return filterForType((List<Object>) value, Image.class, AttributeConstants.HumanProfileAttributes.DOCUMENT_IMAGES);
            default:
                return value;
        }
    }

    private <T> List<T> filterForType(List<Object> values, Class<T> clazz, String attributeName) {
        List<T> list = new ArrayList<>();
        for (Object value : values) {
            if (clazz.isInstance(value)) {
                list.add((T) value);
            } else {
                LOG.warn("Filtering out unexpected instance of '{}' from '{}' multi-value", value.getClass().getCanonicalName(), attributeName);
            }
        }
        return Collections.unmodifiableList(list);
    }

    private List<Anchor> convertAnchors(AttributeProto.Attribute AttributeProto) {
        List<Anchor> entries = new ArrayList<>();
        for (AttributeProto.Anchor anchorProto : AttributeProto.getAnchorsList()) {
            try {
                entries.add(anchorConverter.convert(anchorProto));
            } catch (Exception e) {
                LOG.warn("Failed to read '{}' Anchor for Attribute '{}'", anchorProto.getSubType(), AttributeProto.getName());
                LOG.debug("Converting Anchor on Attribute '{}' resulted in exception '{}'", AttributeProto.getName(), e);
            }
        }
        return Collections.unmodifiableList(entries);
    }

    private List<Anchor> filterAnchors(List<Anchor> anchors, String type) {
        List<Anchor> filtered = new ArrayList<>();
        for (Anchor anchor : anchors) {
            if (type.equals(anchor.getType())) {
                filtered.add(anchor);
            }
        }
        return Collections.unmodifiableList(filtered);
    }

}

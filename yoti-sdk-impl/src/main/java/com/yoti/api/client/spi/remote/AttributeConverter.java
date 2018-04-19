package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.AttrProto.Anchor;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto.ContentType;
import com.yoti.api.client.spi.remote.util.AnchorCertificateParser;
import com.yoti.api.client.spi.remote.util.AnchorType;
import com.yoti.api.client.spi.remote.util.AnchorCertificateParser.AnchorVerifierSourceData;

public class AttributeConverter {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(AttributeConverter.class);

    
    public static Attribute convertAttribute(AttrProto.Attribute attribute) throws ParseException, IOException{
        if (ContentType.STRING.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(), 
                attribute.getValue().toString(DEFAULT_CHARSET), 
                extractMetadata(attribute, AnchorType.SOURCE),
                extractMetadata(attribute, AnchorType.VERIFIER));
        } else if (ContentType.DATE.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                DateAttributeValue.parseFrom(attribute.getValue().toByteArray()),
                extractMetadata(attribute, AnchorType.SOURCE),
                extractMetadata(attribute, AnchorType.VERIFIER));        
        } else if (ContentType.JPEG.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                new JpegAttributeValue(attribute.getValue().toByteArray()),
                extractMetadata(attribute, AnchorType.SOURCE),
                extractMetadata(attribute, AnchorType.VERIFIER));
        } else if (ContentType.PNG.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                new PngAttributeValue(attribute.getValue().toByteArray()),
                extractMetadata(attribute, AnchorType.SOURCE),
                extractMetadata(attribute, AnchorType.VERIFIER));
        } else if (ContentType.JSON.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                JSON_MAPPER.readValue(attribute.getValue().toString(DEFAULT_CHARSET), Map.class),
                extractMetadata(attribute, AnchorType.SOURCE),
                extractMetadata(attribute, AnchorType.VERIFIER));
        }

        LOG.error("Unknown type {} for attribute {}", attribute.getContentType(), attribute.getName());
        return new com.yoti.api.client.Attribute(attribute.getName(), 
                attribute.getValue().toString(DEFAULT_CHARSET),
                extractMetadata(attribute, AnchorType.SOURCE),
                extractMetadata(attribute, AnchorType.VERIFIER));
    }

    private static Set<String> extractMetadata(AttrProto.Attribute attribute, AnchorType anchorType) {
        Set<String> entries = new HashSet<String>();
        for (Anchor anchor : attribute.getAnchorsList()) {
            AnchorVerifierSourceData anchorData = AnchorCertificateParser.getTypesFromAnchor(anchor);
            if(anchorData.getType().equals(anchorType)) {
                entries.addAll(anchorData.getEntries());
            }
        }
        return entries;
    }

    
}

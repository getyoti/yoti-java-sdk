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
import com.yoti.api.client.spi.remote.proto.AttrProto.Anchor;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto.ContentType;
import com.yoti.api.client.spi.remote.util.AnchorCertificateUtils;
import com.yoti.api.client.spi.remote.util.AnchorType;
import com.yoti.api.client.spi.remote.util.AnchorCertificateUtils.AnchorVerifierSourceData;

public class AttributeConverter {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(AttributeConverter.class);

    
    public static Attribute convertAttribute(com.yoti.api.client.spi.remote.proto.AttrProto.Attribute attribute) throws ParseException, IOException{
        if (ContentType.STRING.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(), 
                attribute.getValue().toString(DEFAULT_CHARSET), 
                extractSources(attribute));
        } else if (ContentType.DATE.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                DateAttributeValue.parseFrom(attribute.getValue().toByteArray()),
                extractSources(attribute));        
        } else if (ContentType.JPEG.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                new JpegAttributeValue(attribute.getValue().toByteArray()),
                extractSources(attribute));
        } else if (ContentType.PNG.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                new PngAttributeValue(attribute.getValue().toByteArray()),
                extractSources(attribute));
        } else if (ContentType.JSON.equals(attribute.getContentType())) {
            return new com.yoti.api.client.Attribute(attribute.getName(),
                JSON_MAPPER.readValue(attribute.getValue().toString(DEFAULT_CHARSET), Map.class),
                extractSources(attribute));
        }

        LOG.error("Unknown type {} for attribute {}", attribute.getContentType(), attribute.getName());
        return new com.yoti.api.client.Attribute(attribute.getName(), 
                attribute.getValue().toString(DEFAULT_CHARSET),
                extractSources(attribute));
    }

    private static Set<String> extractSources(com.yoti.api.client.spi.remote.proto.AttrProto.Attribute attribute) {
        Set<String> sources = new HashSet<String>();
        for (Anchor anchor : attribute.getAnchorsList()) {
            AnchorVerifierSourceData anchorData = AnchorCertificateUtils.getTypesFromAnchor(anchor);
            if(anchorData.getType().equals(AnchorType.SOURCE)) {
                sources.addAll(anchorData.getEntries());
            }
        }
        return sources;
    }

    
}

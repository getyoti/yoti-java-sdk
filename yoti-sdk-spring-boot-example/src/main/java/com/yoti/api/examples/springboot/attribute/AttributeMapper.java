package com.yoti.api.examples.springboot.attribute;

import java.io.IOException;

import com.yoti.api.client.Attribute;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.util.StringUtils;

public class AttributeMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static DisplayAttribute mapToDisplayAttribute(Attribute<?> attribute) {
        return AttributeDisplayProperty.fromAttribute(attribute)
                .map(property -> property.isJson()
                        ? new DisplayAttribute(toJson(attribute), property)
                        : new DisplayAttribute(attribute, property)
                )
                .orElseGet(() -> {
                    if (attribute.getName().contains(":")) {
                        return handleAgeVerification(attribute);
                    } else {
                        return handleProfileAttribute(attribute);
                    }
                });
    }

    private static Attribute<String> toJson(Attribute<?> attribute) {
        String json;

        try {
            json = MAPPER.writeValueAsString(attribute.getValue());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return new Attribute<>(attribute.getName(), json);
    }

    private static DisplayAttribute handleAgeVerification(Attribute<?> attribute) {
        return new DisplayAttribute("Age Verification/", "Age verified", attribute, "yoti-icon-verified");
    }

    private static DisplayAttribute handleProfileAttribute(Attribute<?> attribute) {
        return attribute.getName().equalsIgnoreCase("selfie")
                ? null
                : new DisplayAttribute(StringUtils.capitalize(attribute.getName()), attribute, "yoti-icon-profile");
    }

}

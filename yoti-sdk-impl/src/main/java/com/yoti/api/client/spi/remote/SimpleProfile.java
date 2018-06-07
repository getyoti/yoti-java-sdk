package com.yoti.api.client.spi.remote;

import static java.util.Collections.unmodifiableMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;

final class SimpleProfile implements Profile {

    private final Map<String, Attribute> attributes;
    private final Map<String, Attribute> protectedAttributes;

    /**
     * Create a new profile based on a list of attributes
     *
     * @param attributeList
     *            list containing the attributes for this profile
     */
    public SimpleProfile(List<Attribute> attributeList) {
        if (attributeList == null) {
            throw new IllegalArgumentException("Attributes must not be null.");
        }
        this.attributes = createAttributeMap(attributeList);
        this.protectedAttributes = unmodifiableMap(attributes);
    }

    /**
     * Get string attribute by its name.
     *
     * @param name
     *            attribute name.
     * @return String value of attribute, null if the attribute has a different type.
     */
    @Override
    public String getAttribute(String name) {
        Attribute attribute = getAttributeObject(name);
        return (attribute == null) ? null : attribute.getValue(String.class);
    }

    @Override
    public Attribute getAttributeObject(String name) {
        ensureName(name);
        return attributes.get(name);
    }

    /**
     * Get boolean attributes by its name.
     *
     * @param name
     *            attribute name.
     * @param defaultValue
     *            default value if value is null
     * @return boolean value of attribute, defaultValue if the specified attribute has a different type.
     */
    @Override
    public boolean is(String name, boolean defaultValue) {
        Attribute attribute = getAttributeObject(name);
        return (attribute == null) ? defaultValue : attribute.getValueOrDefault(Boolean.class, defaultValue);
    }

    /**
     * Return attribute value for a key.
     *
     * @param name
     *            attribute name
     * @param clazz
     *            attribute type
     * @return typed attribute value, null if attribute type is not assignable from the specified class
     */
    @Override
    public <T> T getAttribute(String name, Class<T> clazz) {
        Attribute attribute = getAttributeObject(name);
        return (attribute == null) ? null : attribute.getValue(clazz);
    }

    @Override
    public <T> T findAttributeStartingWith(String name, Class<T> clazz) {
        Attribute attribute = doFindAttribute(name);
        return (attribute == null) ? null : attribute.getValue(clazz);
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return protectedAttributes.values();
    }

    private Map<String, Attribute> createAttributeMap(List<Attribute> attributes) {
        Map<String, Attribute> result = new HashMap<>();
        for (Attribute a : attributes) {
            result.put(a.getName(), a);
        }
        return result;
    }

    private Attribute doFindAttribute(String name) {
        ensureName(name);
        for (Map.Entry<String, Attribute> entry : attributes.entrySet()) {
            if (entry.getKey().startsWith(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private void ensureName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Attribute name must not be null.");
        }
    }

}

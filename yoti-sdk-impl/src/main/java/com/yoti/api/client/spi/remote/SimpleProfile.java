package com.yoti.api.client.spi.remote;

import static java.util.Collections.unmodifiableMap;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

final class SimpleProfile implements Profile {

    private final Map<String, Attribute> attributes;
    private final Map<String, Attribute> protectedAttributes;

    /**
     * Create a new profile based on an attribute map.
     *
     * @param objectAttributes
     *            Map holding the values for the attributes
     */
    public SimpleProfile(Map<String, Object> objectAttributes) {
        if (objectAttributes == null) {
            throw new IllegalArgumentException("Attributes must not be null.");
        }
        this.attributes = createAttributeMap(objectAttributes);
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
        Attribute attribute = doGetAttribute(name);
        return (attribute == null) ? null : attribute.getValue(String.class);
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
        Attribute attribute = doGetAttribute(name);
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
        Attribute attribute = doGetAttribute(name);
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

    private Map<String, Attribute> createAttributeMap(Map<String, Object> objectAttributes) {
        Map<String, Attribute> result = new HashMap<String, Attribute>();
        for (Map.Entry<String, Object> e : objectAttributes.entrySet()) {
            result.put(e.getKey(), new Attribute(e.getKey(), e.getValue()));
        }
        return result;
    }

    private Attribute doGetAttribute(String name) {
        ensureName(name);
        return attributes.get(name);
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

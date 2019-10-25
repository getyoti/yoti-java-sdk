package com.yoti.api.client.spi.remote;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;

final class SimpleProfile implements Profile {

    private final List<Attribute<?>> protectedAttributes;

    /**
     * Create a new profile based on a list of attributes
     *
     * @param attributeList list containing the attributes for this profile
     */
    public SimpleProfile(List<Attribute<?>> attributeList) {
        if (attributeList == null) {
            throw new IllegalArgumentException("Attributes must not be null.");
        }
        this.protectedAttributes = unmodifiableList(attributeList);
    }

    /**
     * Return attribute value for a key.
     *
     * @param name  attribute name
     * @param clazz attribute type
     * @return typed attribute value, null if attribute type is not assignable from the specified class
     */
    @Override
    public <T> Attribute<T> getAttribute(String name, Class<T> clazz) {
        ensureName(name);
        Attribute<?> attribute = doFindAttribute(name);
        return castSafely(clazz, attribute);
    }

    @Override
    public Attribute getAttribute(String name) {
        ensureName(name);
        return doFindAttribute(name);
    }

    @Override
    public <T> List<Attribute<T>> findAttributesStartingWith(String name, Class<T> clazz) {
        ensureName(name);
        List<Attribute<T>> matches = new ArrayList<>();
        for (Attribute<?> entry : protectedAttributes) {
            if (entry.getName().startsWith(name)) {
                Attribute<T> value = castSafely(clazz, entry);
                matches.add(value);
            }
        }
        return matches;
    }

    @Override
    public <T> Attribute<T> findAttributeStartingWith(String name, Class<T> clazz) {
        ensureName(name);
        Attribute<?> attribute = doFindAttribute(name);
        return castSafely(clazz, attribute);
    }

    @Override
    public Collection<Attribute<?>> getAttributes() {
        return protectedAttributes;
    }

    private Attribute<?> doFindAttribute(String name) {
        ensureName(name);
        for (Attribute<?> entry : protectedAttributes) {
            if (entry.getName().startsWith(name)) {
                return entry;
            }
        }
        return null;
    }

    private void ensureName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Attribute name must not be null.");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Attribute<T> castSafely(Class<T> clazz, Attribute<?> attribute) {
        if (attribute != null) {
            Class valueType = attribute.getValue().getClass();
            if (!clazz.isAssignableFrom(valueType)) {
                throw new ClassCastException(String.format("Cannot cast from '%s' to '%s'", valueType.getCanonicalName(), clazz.getCanonicalName()));
            }
        }
        return (Attribute<T>) attribute;
    }


}

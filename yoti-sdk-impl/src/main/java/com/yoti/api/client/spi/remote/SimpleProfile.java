package com.yoti.api.client.spi.remote;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

import java.util.*;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;

final class SimpleProfile implements Profile {

    private final Map<String, List<Attribute<?>>> protectedAttributes;

    /**
     * Create a new profile based on a list of attributes
     *
     * @param attributeList list containing the attributes for this profile
     */
    public SimpleProfile(List<Attribute<?>> attributeList) {
        if (attributeList == null) {
            throw new IllegalArgumentException("Attributes must not be null.");
        }
        this.protectedAttributes = unmodifiableMap(createAttributeMap(attributeList));
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
        Attribute<?> attribute = getAttribute(name);
        return castSafely(clazz, attribute);
    }

    @Override
    public Attribute getAttribute(String name) {
        ensureName(name);
        for (Map.Entry<String, List<Attribute<?>>> entry : protectedAttributes.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue().get(0);
            }
        }
        return null;
    }

    /**
     * Return a list of {@link Attribute}s that match
     * the exact name
     *
     * @param name  the name of the {@link Attribute}s
     * @param clazz the type of the {@link Attribute} value
     * @return typed list of attribute, empty list if there are no matching attributes on the profile
     */
    @Override
    public <T> List<Attribute<T>> getAttributesByName(String name, Class<T> clazz) {
        ensureName(name);
        List<Attribute<T>> matches = new ArrayList<>();
        for (Map.Entry<String, List<Attribute<?>>> entry : protectedAttributes.entrySet()) {
            if (entry.getKey().equals(name)) {
                for (Attribute<?> attribute : entry.getValue()) {
                    Attribute<T> value = castSafely(clazz, attribute);
                    matches.add(value);
                }
            }
        }
        return matches;
    }

    @Override
    public <T> List<Attribute<T>> findAttributesStartingWith(String name, Class<T> clazz) {
        ensureName(name);
        List<Attribute<T>> matches = new ArrayList<>();
        for (Map.Entry<String, List<Attribute<?>>> entry : protectedAttributes.entrySet()) {
            if (entry.getKey().startsWith(name)) {
                for (Attribute<?> attribute : entry.getValue()) {
                    Attribute<T> value = castSafely(clazz, attribute);
                    matches.add(value);
                }
            }
        }
        return matches;
    }

    @Override
    public <T> Attribute<T> findAttributeStartingWith(String name, Class<T> clazz) {
        ensureName(name);
        Attribute<?> attribute = findAttributeStartingWith(name);
        return castSafely(clazz, attribute);
    }

    private Attribute<?> findAttributeStartingWith(String name) {
        ensureName(name);
        for (Map.Entry<String, List<Attribute<?>>> entry : protectedAttributes.entrySet()) {
            if (entry.getKey().startsWith(name)) {
                return entry.getValue().get(0);
            }
        }
        return null;
    }

    @Override
    public Collection<Attribute<?>> getAttributes() {
        List<Attribute<?>> attributes = new ArrayList<>();
        for (Map.Entry<String, List<Attribute<?>>> entry : protectedAttributes.entrySet()) {
            attributes.addAll(entry.getValue());
        }
        return attributes;
    }

    private Map<String, List<Attribute<?>>> createAttributeMap(List<Attribute<?>> attributes) {
        Map<String, List<Attribute<?>>> result = new HashMap<>();
        for (Attribute<?> a : attributes) {
            if (!result.containsKey(a.getName())) {
                result.put(a.getName(), new ArrayList<Attribute<?>>());
            }

            List<Attribute<?>> modifiable = result.get(a.getName());
            modifiable.add(a);
            result.put(a.getName(), modifiable);
        }
        return result;
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

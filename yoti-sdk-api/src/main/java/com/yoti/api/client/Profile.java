package com.yoti.api.client;

import static java.util.Collections.unmodifiableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Yoti profile for a connect token and application. You can get a hold of one of these by using a {@link YotiClient}.
 */
public abstract class Profile {

    private final Map<String, List<Attribute<?>>> protectedAttributes;

    /**
     * Create a new profile based on a list of attributes
     *
     * @param attributeList list containing the attributes for this profile
     */
    Profile(List<Attribute<?>> attributeList) {
        if (attributeList == null) {
            throw new IllegalArgumentException("Attributes must not be null.");
        }
        this.protectedAttributes = unmodifiableMap(createAttributeMap(attributeList));
    }

    /**
     * Return typed {@link Attribute} object for a key.
     *
     * @param <T>   the type parameter indicating the type of the returned value
     * @param name  attribute name
     * @param clazz attribute type
     * @return typed attribute, null if it is not present in the profile
     */
    public <T> Attribute<T> getAttribute(String name, Class<T> clazz) {
        Attribute<?> attribute = getAttribute(name);
        return castSafely(clazz, attribute);
    }

    /**
     * Returns the {@link Attribute} object for the key
     *
     * @param name the name of the attribute
     * @return the attribute object, null if it is not present in the profile
     */
    public Attribute<?> getAttribute(String name) {
        ensureName(name);
        List<Attribute<?>> attributes = protectedAttributes.get(name);
        if (attributes != null && attributes.size() > 0) {
            return attributes.get(0);
        }
        return null;
    }

    /**
     * Return a list of all the {@link Attribute}s with a name starting with <code>name</code>
     *
     * @param <T>   the type parameter indicating the type of the returned value
     * @param name  attribute name
     * @param clazz attribute type
     * @return typed attribute value, <code>null</code> if there was no match
     */
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

    /**
     * Return the first {@link Attribute} with a name starting with <code>name</code>
     *
     * @param <T>   the type parameter indicating the type of the returned value
     * @param name  attribute name
     * @param clazz attribute type
     * @return typed attribute value, <code>null</code> if there was no match
     */
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

    /**
     * Return all {@link Attribute}s for the profile.
     *
     * @return an unsorted collection of {@link Attribute}s
     */
    public Collection<Attribute<?>> getAttributes() {
        List<Attribute<?>> attributes = new ArrayList<>();
        for (Map.Entry<String, List<Attribute<?>>> entry : protectedAttributes.entrySet()) {
            attributes.addAll(entry.getValue());
        }
        return attributes;
    }

    /**
     * Return typed {@link Attribute} by ID.
     *
     * @param id the ID to match
     * @return attribute value, <code>null</code> if there was no match
     */
    public Attribute<?> getAttributeById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Attribute ID must not be null");
        }

        return protectedAttributes.values()
                .stream()
                .flatMap(List::stream)
                .filter(attribute -> Objects.equals(attribute.getId(), id))
                .findFirst()
                .orElse(null);
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
            Class<?> valueType = attribute.getValue().getClass();
            if (!clazz.isAssignableFrom(valueType)) {
                throw new ClassCastException(String.format("Cannot cast from '%s' to '%s'", valueType.getCanonicalName(), clazz.getCanonicalName()));
            }
        }
        return (Attribute<T>) attribute;
    }

}

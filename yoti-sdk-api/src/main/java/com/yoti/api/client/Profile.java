package com.yoti.api.client;

import java.util.Collection;

/**
 * Yoti profile for a connect token and application. You can get a hold of one of these by using a {@link YotiClient}.
 */
public interface Profile {

    /**
     * Return typed attribute value for a key.
     *
     * @param <T> the type parameter indicating the type of the returned value
     * @param name attribute name
     * @param clazz attribute type
     * @return typed attribute, null if attribute type is not assignable from the specified class
     */
    <T> Attribute<T> getAttribute(String name, Class<T> clazz);

    /**
     * Returns the attribute object for the key
     *
     * @param name
     * @return the attribute object, null if it is not present in the profile
     */
    Attribute getAttribute(String name);

    /**
     * Return the value of the first attribute with a name starting with <code>name</code>
     *
     * @param <T>   the type parameter indicating the type of the returned value
     * @param name  attribute name
     * @param clazz attribute type
     * @return typed attribute value, null if there was no match or attribute type is not assignable from the specified class
     */
    <T> Attribute<T> findAttributeStartingWith(String name, Class<T> clazz);

    /**
     * Return all attributes for the profile.
     *
     * @return an unsorted collection of attributes
     */
    Collection<Attribute<?>> getAttributes();

}

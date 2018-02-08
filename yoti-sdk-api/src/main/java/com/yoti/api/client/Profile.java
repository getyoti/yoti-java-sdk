package com.yoti.api.client;

import java.util.Collection;

/**
 * Yoti profile for a connect token and application. You can get a hold of one of these by using a {@link YotiClient}.
 *
 */
public interface Profile {
    /**
     * Get string attribute by its name.
     *
     * @param name
     *            attribute name.
     * @return String value of attribute, null if the attribute has a different type.
     */
    String getAttribute(String name);

    /**
     * Get boolean attribute by its name.
     *
     * @param name
     *            attribute name.
     * @param defaultValue
     *            default value if value is null
     * @return boolean value of attribute, defaultValue if the specified attribute has a different type.
     */
    boolean is(String name, boolean defaultValue);

    /**
     * Return typed attribute value for a key.
     * 
     * @param <T> the type parameter indicating the type of the returned value
     *
     * @param name
     *            attribute name
     * @param clazz
     *            attribute type
     * @return typed attribute value, null if attribute type is not assignable from the specified class
     */
    <T> T getAttribute(String name, Class<T> clazz);

    /**
     * Return the value of the first attribute with a name starting with <code>name</code>
     *
     * @param <T> the type parameter indicating the type of the returned value
     *
     * @param name
     *            attribute name
     * @param clazz
     *            attribute type
     * @return typed attribute value, null if there was no match or attribute type is not assignable from the specified class
     */
    <T> T findAttributeStartingWith(String name, Class<T> clazz);

    /**
     * Return all attributes for the profile.
     *
     * @return an unsorted collection of attributes
     *
     */
    Collection<Attribute> getAttributes();
}

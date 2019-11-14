package com.yoti.api.client.spi.remote;

import java.util.Collection;
import java.util.List;

import com.yoti.api.attributes.AttributeConstants;
import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.Image;
import com.yoti.api.client.Profile;

/**
 * Adapter linking Profile and ApplicationProfile together by wrapping the latter and exposing well-known attributes.
 */
public final class ApplicationProfileAdapter implements ApplicationProfile {

    private final Profile wrapped;

    private ApplicationProfileAdapter(Profile wrapped) {
        this.wrapped = wrapped;
    }

    public static ApplicationProfileAdapter wrap(Profile wrapped) {
        return new ApplicationProfileAdapter(wrapped);
    }

    @Override
    public Attribute getAttribute(String name) {
        return wrapped.getAttribute(name);
    }

    @Override
    public <T> Attribute<T> getAttribute(String name, Class<T> clazz) {
        return wrapped.getAttribute(name, clazz);
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
        return wrapped.getAttributesByName(name, clazz);
    }

    @Override
    public <T> List<Attribute<T>> findAttributesStartingWith(String name, Class<T> clazz) {
        return wrapped.findAttributesStartingWith(name, clazz);
    }

    @Override
    public <T> Attribute<T> findAttributeStartingWith(String name, Class<T> clazz) {
        return wrapped.findAttributeStartingWith(name, clazz);
    }

    @Override
    public Collection<Attribute<?>> getAttributes() {
        return wrapped.getAttributes();
    }

    @Override
    public Attribute<String> getApplicationName() {
        return wrapped.getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_NAME, String.class);
    }

    @Override
    public Attribute<String> getApplicationUrl() {
        return wrapped.getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_URL, String.class);
    }

    @Override
    public Attribute<String> getApplicationReceiptBgColor() {
        return wrapped.getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_RECEIPT_BGCOLOR, String.class);
    }

    @Override
    public Attribute<Image> getApplicationLogo() {
        return wrapped.getAttribute(AttributeConstants.ApplicationProfileAttributes.ATTRIBUTE_APPLICATION_LOGO, Image.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wrapped == null) ? 0 : wrapped.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ApplicationProfileAdapter other = (ApplicationProfileAdapter) obj;
        if (wrapped == null) {
            if (other.wrapped != null) {
                return false;
            }
        } else if (!wrapped.equals(other.wrapped)) {
            return false;
        }
        return true;
    }

}

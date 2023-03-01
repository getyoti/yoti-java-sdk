package com.yoti.api.client.identity;

import java.net.URI;
import java.util.List;

import com.yoti.api.client.identity.extension.Extension;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareSessionQrCode {

    @JsonProperty(Property.ID)
    private String id;

    @JsonProperty(Property.URI)
    private URI uri;

    @JsonProperty(Property.EXPIRY)
    private String expiry;

    @JsonProperty(Property.POLICY)
    private String policy;

    @JsonProperty(Property.EXTENSIONS)
    private List<Extension<?>> extensions;

    @JsonProperty(Property.SESSION)
    private ShareSession session;

    @JsonProperty(Property.REDIRECT_URI)
    private URI redirectUri;

    public String getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getPolicy() {
        return policy;
    }

    public List<Extension<?>> getExtensions() {
        return extensions;
    }

    public ShareSession getSession() {
        return session;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }

    private static final class Property {

        private static final String ID = "id";
        private static final String URI = "uri";
        private static final String EXPIRY = "expiry";
        private static final String POLICY = "policy";
        private static final String EXTENSIONS = "extensions";
        private static final String SESSION = "session";
        private static final String REDIRECT_URI = "redirectUri";

    }

}

package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.System.nanoTime;
import static java.util.UUID.randomUUID;

public class PathFactory {

    private static final String SIGNATURE_PARAMS = "nonce=%s&timestamp=%s";

    private UnsignedPathFactory unsignedPathFactory;

    public PathFactory() {
        this.unsignedPathFactory = new UnsignedPathFactory();
    }

    public String createSignatureParams() {
        return String.format(SIGNATURE_PARAMS, randomUUID(), createTimestamp());
    }

    public String createProfilePath(String appId, String connectToken) {
        return unsignedPathFactory.createProfilePath(appId, connectToken) + "&" + createSignatureParams();
    }

    public String createAmlPath(String appId) {
        return unsignedPathFactory.createAmlPath(appId) + "&" + createSignatureParams();
    }

    public String createDynamicSharingPath(String appId) {
        return unsignedPathFactory.createDynamicSharingPath(appId) + "?" + createSignatureParams();
    }

    public String createNewYotiDocsSessionPath(String appId) {
        return unsignedPathFactory.createNewYotiDocsSessionPath(appId) + "&" + createSignatureParams();
    }

    public String createGetYotiDocsSessionPath(String appId, String sessionId) {
        return unsignedPathFactory.createYotiDocsSessionPath(appId, sessionId) + "&" + createSignatureParams();
    }

    public String createMediaContentPath(String appId, String sessionId, String mediaId) {
        return unsignedPathFactory.createMediaContentPath(appId, sessionId, mediaId) + "&" + createSignatureParams();
    }

    public String createGetSupportedDocumentsPath(boolean includeNonLatin) {
        return unsignedPathFactory.createGetSupportedDocumentsPath(includeNonLatin) + "&" + createSignatureParams();
    }

    protected long createTimestamp() {
        return nanoTime() / 1000;
    }

}

package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.String.format;

public class UnsignedPathFactory {

    static final String PROFILE_PATH_TEMPLATE = "/profile/%s?appId=%s";
    static final String AML_PATH_TEMPLATE = "/aml-check?appId=%s";
    static final String QR_CODE_PATH_TEMPLATE = "/qrcodes/apps/%s";
    static final String DOCS_CREATE_SESSION_PATH_TEMPLATE = "/sessions?sdkId=%s";
    static final String DOCS_SESSION_PATH_TEMPLATE = "/sessions/%s?sdkId=%s";
    static final String DOCS_MEDIA_CONTENT_PATH_TEMPLATE = "/sessions/%s/media/%s/content?sdkId=%s";
    static final String DOCS_PUT_IBV_INSTRUCTIONS_PATH_TEMPLATE = "/sessions/%s/instructions?sdkId=%s";
    static final String DOCS_FETCH_IBV_INSTRUCTIONS_PATH_TEMPLATE = "/sessions/%s/instructions?sdkId=%s";
    static final String DOCS_SUPPORTED_DOCUMENTS_PATH = "/supported-documents";

    public String createProfilePath(String appId, String connectToken) {
        return format(PROFILE_PATH_TEMPLATE, connectToken, appId);
    }

    public String createAmlPath(String appId) {
        return format(AML_PATH_TEMPLATE, appId);
    }

    public String createDynamicSharingPath(String appId) {
        return format(QR_CODE_PATH_TEMPLATE, appId);
    }

    public String createNewYotiDocsSessionPath(String appId) {
        return format(DOCS_CREATE_SESSION_PATH_TEMPLATE, appId);
    }

    public String createYotiDocsSessionPath(String appId, String sessionId) {
        return format(DOCS_SESSION_PATH_TEMPLATE, sessionId, appId);
    }

    public String createMediaContentPath(String appId, String sessionId, String mediaId) {
        return format(DOCS_MEDIA_CONTENT_PATH_TEMPLATE, sessionId, mediaId, appId);
    }

    public String createPutIbvInstructionsPath(String appId, String sessionId) {
        return format(DOCS_PUT_IBV_INSTRUCTIONS_PATH_TEMPLATE, sessionId, appId);
    }

    public String createFetchIbvInstructionsPath(String appId, String sessionId) {
        return format(DOCS_FETCH_IBV_INSTRUCTIONS_PATH_TEMPLATE, sessionId, appId);
    }

    public String createGetSupportedDocumentsPath() {
        return DOCS_SUPPORTED_DOCUMENTS_PATH;
    }

}

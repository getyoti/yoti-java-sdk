package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.String.format;

public class UnsignedPathFactory {

    private static final String PROFILE_PATH_TEMPLATE = "/profile/%s?appId=%s";
    private static final String AML_PATH_TEMPLATE = "/aml-check?appId=%s";
    private static final String QR_CODE_PATH_TEMPLATE = "/qrcodes/apps/%s";
    private static final String DOCS_CREATE_SESSION_PATH_TEMPLATE = "/sessions?sdkId=%s";
    private static final String DOCS_SESSION_PATH_TEMPLATE = "/sessions/%s?sdkId=%s";
    private static final String DOCS_MEDIA_CONTENT_PATH_TEMPLATE = "/sessions/%s/media/%s/content?sdkId=%s";
    private static final String DOCS_PUT_IBV_INSTRUCTIONS_PATH_TEMPLATE = "/sessions/%s/instructions?sdkId=%s";
    private static final String DOCS_FETCH_IBV_INSTRUCTIONS_PATH_TEMPLATE = "/sessions/%s/instructions?sdkId=%s";
    private static final String DOCS_FETCH_IBV_INSTRUCTIONS_PDF_PATH_TEMPLATE = "/sessions/%s/instructions/pdf?sdkId=%s";
    private static final String DOCS_SUPPORTED_DOCUMENTS_PATH = "/supported-documents";
    private static final String DOCS_FETCH_INSTRUCTION_CONTACT_PROFILE_PATH_TEMPLATE = "/sessions/%s/instructions/contact-profile?sdkId=%s";
    private static final String DOCS_FETCH_SESSION_CONFIGURATION_PATH_TEMPLATE = "/sessions/%s/configuration?sdkId=%s";
    private static final String DOCS_NEW_FACE_CAPTURE_RESOURCE_PATH_TEMPLATE = "/sessions/%s/resources/face-capture?sdkId=%s";
    private static final String DOCS_UPLOAD_FACE_CAPTURE_IMAGE_PATH_TEMPLATE = "/sessions/%s/resources/face-capture/%s/image?sdkId=%s";
    private static final String DOCS_TRIGGER_IBV_NOTIFICATION_PATH_TEMPLATE = "/sessions/%s/instructions/email?sdkId=%s";
    private static final String IDENTITY_SESSION_CREATION_TEMPLATE = "/v2/sessions";

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

    public String createFetchInstructionsContactProfilePath(String appId, String sessionId) {
        return format(DOCS_FETCH_INSTRUCTION_CONTACT_PROFILE_PATH_TEMPLATE, sessionId, appId);
    }

    public String createGetSupportedDocumentsPath() {
        return DOCS_SUPPORTED_DOCUMENTS_PATH;
    }

    public String createFetchIbvInstructionsPdfPath(String sdkId, String sessionId) {
        return format(DOCS_FETCH_IBV_INSTRUCTIONS_PDF_PATH_TEMPLATE, sessionId, sdkId);
    }

    public String createNewFaceCaptureResourcePath(String sdkId, String sessionId) {
        return format(DOCS_NEW_FACE_CAPTURE_RESOURCE_PATH_TEMPLATE, sessionId, sdkId);
    }

    public String createUploadFaceCaptureImagePath(String sdkId, String sessionId, String resourceId) {
        return format(DOCS_UPLOAD_FACE_CAPTURE_IMAGE_PATH_TEMPLATE, sessionId, resourceId, sdkId);
    }

    public String createFetchSessionConfigurationPath(String sdkId, String sessionId) {
        return format(DOCS_FETCH_SESSION_CONFIGURATION_PATH_TEMPLATE, sessionId, sdkId);
    }

    public String createTriggerIbvEmailNotificationPath(String sdkId, String sessionId) {
        return format(DOCS_TRIGGER_IBV_NOTIFICATION_PATH_TEMPLATE, sessionId, sdkId);
    }

    public String createIdentitySessionPath() {
        return IDENTITY_SESSION_CREATION_TEMPLATE;
    }

}

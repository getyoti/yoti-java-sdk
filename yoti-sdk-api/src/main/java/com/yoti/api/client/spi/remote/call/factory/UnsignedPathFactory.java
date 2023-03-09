package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.String.format;

public class UnsignedPathFactory {

    // AML
    private static final String AML = "/aml-check?appId=%s";

    // Share V2
    private static final String IDENTITY_SESSION_CREATION = "/v2/sessions";
    private static final String IDENTITY_SESSION_RETRIEVAL = "/v2/sessions/%s";
    private static final String IDENTITY_SESSION_QR_CODE_CREATION = "/v2/sessions/%s/qr-codes";
    private static final String IDENTITY_SESSION_QR_CODE_RETRIEVAL = "/v2/qr-codes/%s";

    // Share V1
    private static final String PROFILE = "/profile/%s?appId=%s";
    private static final String QR_CODE = "/qrcodes/apps/%s";

    // Docs
    private static final String DOCS_CREATE_SESSION = "/sessions?sdkId=%s";
    private static final String DOCS_SESSION = "/sessions/%s?sdkId=%s";
    private static final String DOCS_MEDIA_CONTENT = "/sessions/%s/media/%s/content?sdkId=%s";
    private static final String DOCS_PUT_IBV_INSTRUCTIONS = "/sessions/%s/instructions?sdkId=%s";
    private static final String DOCS_FETCH_IBV_INSTRUCTIONS = "/sessions/%s/instructions?sdkId=%s";
    private static final String DOCS_FETCH_IBV_INSTRUCTIONS_PDF = "/sessions/%s/instructions/pdf?sdkId=%s";
    private static final String DOCS_SUPPORTED_DOCUMENTS = "/supported-documents";
    private static final String DOCS_FETCH_INSTRUCTION_CONTACT_PROFILE = "/sessions/%s/instructions/contact-profile?sdkId=%s";
    private static final String DOCS_FETCH_SESSION_CONFIGURATION = "/sessions/%s/configuration?sdkId=%s";
    private static final String DOCS_NEW_FACE_CAPTURE_RESOURCE = "/sessions/%s/resources/face-capture?sdkId=%s";
    private static final String DOCS_UPLOAD_FACE_CAPTURE_IMAGE = "/sessions/%s/resources/face-capture/%s/image?sdkId=%s";
    private static final String DOCS_TRIGGER_IBV_NOTIFICATION = "/sessions/%s/instructions/email?sdkId=%s";

    public String createAmlPath(String appId) {
        return format(AML, appId);
    }

    public String createIdentitySessionPath() {
        return IDENTITY_SESSION_CREATION;
    }

    public String createIdentitySessionRetrievalPath(String sessionId) {
        return format(IDENTITY_SESSION_RETRIEVAL, sessionId);
    }

    public String createIdentitySessionQrCodePath(String sessionId) {
        return format(IDENTITY_SESSION_QR_CODE_CREATION, sessionId);
    }

    public String createIdentitySessionQrCodeRetrievalPath(String qrCodeId) {
        return format(IDENTITY_SESSION_QR_CODE_RETRIEVAL, qrCodeId);
    }

    public String createProfilePath(String appId, String connectToken) {
        return format(PROFILE, connectToken, appId);
    }

    public String createDynamicSharingPath(String appId) {
        return format(QR_CODE, appId);
    }

    public String createNewYotiDocsSessionPath(String appId) {
        return format(DOCS_CREATE_SESSION, appId);
    }

    public String createYotiDocsSessionPath(String appId, String sessionId) {
        return format(DOCS_SESSION, sessionId, appId);
    }

    public String createMediaContentPath(String appId, String sessionId, String mediaId) {
        return format(DOCS_MEDIA_CONTENT, sessionId, mediaId, appId);
    }

    public String createPutIbvInstructionsPath(String appId, String sessionId) {
        return format(DOCS_PUT_IBV_INSTRUCTIONS, sessionId, appId);
    }

    public String createFetchIbvInstructionsPath(String appId, String sessionId) {
        return format(DOCS_FETCH_IBV_INSTRUCTIONS, sessionId, appId);
    }

    public String createFetchIbvInstructionsPdfPath(String sdkId, String sessionId) {
        return format(DOCS_FETCH_IBV_INSTRUCTIONS_PDF, sessionId, sdkId);
    }

    public String createGetSupportedDocumentsPath() {
        return DOCS_SUPPORTED_DOCUMENTS;
    }

    public String createFetchInstructionsContactProfilePath(String appId, String sessionId) {
        return format(DOCS_FETCH_INSTRUCTION_CONTACT_PROFILE, sessionId, appId);
    }

    public String createFetchSessionConfigurationPath(String sdkId, String sessionId) {
        return format(DOCS_FETCH_SESSION_CONFIGURATION, sessionId, sdkId);
    }

    public String createNewFaceCaptureResourcePath(String sdkId, String sessionId) {
        return format(DOCS_NEW_FACE_CAPTURE_RESOURCE, sessionId, sdkId);
    }

    public String createUploadFaceCaptureImagePath(String sdkId, String sessionId, String resourceId) {
        return format(DOCS_UPLOAD_FACE_CAPTURE_IMAGE, sessionId, resourceId, sdkId);
    }

    public String createTriggerIbvEmailNotificationPath(String sdkId, String sessionId) {
        return format(DOCS_TRIGGER_IBV_NOTIFICATION, sessionId, sdkId);
    }

}

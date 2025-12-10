package com.yoti.api.client.spi.remote.call.factory;

import static java.lang.String.format;

public class UnsignedPathFactory {

    // AML
    private static final String AML = "/aml-check";

    // Share V2
    private static final String IDENTITY_SESSION_CREATION = "/v2/sessions";
    private static final String IDENTITY_SESSION_RETRIEVAL = "/v2/sessions/%s";
    private static final String IDENTITY_SESSION_QR_CODE_CREATION = "/v2/sessions/%s/qr-codes";
    private static final String IDENTITY_SESSION_QR_CODE_RETRIEVAL = "/v2/qr-codes/%s";
    private static final String IDENTITY_SESSION_RECEIPT_RETRIEVAL = "/v2/receipts/%s";
    private static final String IDENTITY_SESSION_RECEIPT_KEY_RETRIEVAL = "/v2/wrapped-item-keys/%s";

    // Match
    private static final String DIGITAL_ID_MATCH = "/v1/matches";

    // Share V1
    private static final String PROFILE = "/profile/%s";
    private static final String QR_CODE = "/qrcodes/apps/%s";

    // Docs
    private static final String DOCS_CREATE_SESSION = "/sessions";
    private static final String DOCS_SESSION = "/sessions/%s";
    private static final String DOCS_MEDIA_CONTENT = "/sessions/%s/media/%s/content";
    private static final String DOCS_PUT_IBV_INSTRUCTIONS = "/sessions/%s/instructions";
    private static final String DOCS_FETCH_IBV_INSTRUCTIONS = "/sessions/%s/instructions";
    private static final String DOCS_FETCH_IBV_INSTRUCTIONS_PDF = "/sessions/%s/instructions/pdf";
    private static final String DOCS_SUPPORTED_DOCUMENTS = "/supported-documents?includeNonLatin=%s";
    private static final String DOCS_FETCH_INSTRUCTION_CONTACT_PROFILE = "/sessions/%s/instructions/contact-profile";
    private static final String DOCS_FETCH_SESSION_CONFIGURATION = "/sessions/%s/configuration";
    private static final String DOCS_NEW_FACE_CAPTURE_RESOURCE = "/sessions/%s/resources/face-capture";
    private static final String DOCS_UPLOAD_FACE_CAPTURE_IMAGE = "/sessions/%s/resources/face-capture/%s/image";
    private static final String DOCS_TRIGGER_IBV_NOTIFICATION = "/sessions/%s/instructions/email";
    private static final String DOCS_TRACKED_DEVICES = "/sessions/%s/tracked-devices";

    public String createAmlPath() {
        return format(AML);
    }

    public String createIdentitySessionPath() {
        return IDENTITY_SESSION_CREATION;
    }

    public String createIdentitySessionRetrievalPath(String sessionId) {
        return format(IDENTITY_SESSION_RETRIEVAL, base64ToBase64url(sessionId));
    }

    public String createIdentitySessionQrCodePath(String sessionId) {
        return format(IDENTITY_SESSION_QR_CODE_CREATION, base64ToBase64url(sessionId));
    }

    public String createIdentitySessionQrCodeRetrievalPath(String qrCodeId) {
        return format(IDENTITY_SESSION_QR_CODE_RETRIEVAL, base64ToBase64url(qrCodeId));
    }

    public String createIdentitySessionReceiptRetrievalPath(String receiptId) {
        return format(IDENTITY_SESSION_RECEIPT_RETRIEVAL, base64ToBase64url(receiptId));
    }

    public String createIdentitySessionReceiptKeyRetrievalPath(String wrappedItemKeyId) {
        return format(IDENTITY_SESSION_RECEIPT_KEY_RETRIEVAL, base64ToBase64url(wrappedItemKeyId));
    }

    private static String base64ToBase64url(String value) {
        return value.replace('+', '-').replace('/', '_');
    }

    public String createIdentityMatchPath() {
        return DIGITAL_ID_MATCH;
    }

    public String createProfilePath(String connectToken) {
        return format(PROFILE, connectToken);
    }

    public String createDynamicSharingPath(String appId) {
        return format(QR_CODE, appId);
    }

    public String createNewYotiDocsSessionPath() {
        return DOCS_CREATE_SESSION;
    }

    public String createYotiDocsSessionPath(String sessionId) {
        return format(DOCS_SESSION, sessionId);
    }

    public String createMediaContentPath(String sessionId, String mediaId) {
        return format(DOCS_MEDIA_CONTENT, sessionId, mediaId);
    }

    public String createPutIbvInstructionsPath(String sessionId) {
        return format(DOCS_PUT_IBV_INSTRUCTIONS, sessionId);
    }

    public String createFetchIbvInstructionsPath(String sessionId) {
        return format(DOCS_FETCH_IBV_INSTRUCTIONS, sessionId);
    }

    public String createFetchIbvInstructionsPdfPath(String sessionId) {
        return format(DOCS_FETCH_IBV_INSTRUCTIONS_PDF, sessionId);
    }

    public String createGetSupportedDocumentsPath(boolean includeNonLatin) {
        return format(DOCS_SUPPORTED_DOCUMENTS, includeNonLatin);
    }

    public String createFetchInstructionsContactProfilePath(String sessionId) {
        return format(DOCS_FETCH_INSTRUCTION_CONTACT_PROFILE, sessionId);
    }

    public String createFetchSessionConfigurationPath(String sessionId) {
        return format(DOCS_FETCH_SESSION_CONFIGURATION, sessionId);
    }

    public String createNewFaceCaptureResourcePath(String sessionId) {
        return format(DOCS_NEW_FACE_CAPTURE_RESOURCE, sessionId);
    }

    public String createUploadFaceCaptureImagePath(String sessionId, String resourceId) {
        return format(DOCS_UPLOAD_FACE_CAPTURE_IMAGE, sessionId, resourceId);
    }

    public String createTriggerIbvEmailNotificationPath(String sessionId) {
        return format(DOCS_TRIGGER_IBV_NOTIFICATION, sessionId);
    }

    public String createFetchTrackedDevices(String sessionId) {
        return format(DOCS_TRACKED_DEVICES, sessionId);
    }

    public String createDeleteTrackedDevices(String sessionId) {
        return format(DOCS_TRACKED_DEVICES, sessionId);
    }

}

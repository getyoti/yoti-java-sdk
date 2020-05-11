package com.yoti.api.client.sandbox.docs.request;

import static com.yoti.api.client.docs.DocScanConstants.ID_DOCUMENT_AUTHENTICITY;
import static com.yoti.api.client.docs.DocScanConstants.ID_DOCUMENT_FACE_MATCH;
import static com.yoti.api.client.docs.DocScanConstants.ID_DOCUMENT_TEXT_DATA_CHECK;
import static com.yoti.api.client.docs.DocScanConstants.LIVENESS;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.sandbox.docs.request.check.SandboxDocumentAuthenticityCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxFaceMatchCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxLivenessCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxTextDataCheck;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SandboxCheckReports {

    @JsonProperty(ID_DOCUMENT_TEXT_DATA_CHECK)
    private final List<SandboxTextDataCheck> textDataChecks;

    @JsonProperty(ID_DOCUMENT_AUTHENTICITY)
    private final List<SandboxDocumentAuthenticityCheck> documentAuthenticityChecks;

    @JsonProperty(LIVENESS)
    private final List<SandboxLivenessCheck> livenessChecks;

    @JsonProperty(ID_DOCUMENT_FACE_MATCH)
    private final List<SandboxFaceMatchCheck> documentFaceMatchChecks;

    @JsonProperty("async_report_delay")
    private final Integer asyncReportDelay;

    SandboxCheckReports(List<SandboxTextDataCheck> textDataChecks,
            List<SandboxDocumentAuthenticityCheck> documentAuthenticityChecks,
            List<SandboxLivenessCheck> livenessChecks,
            List<SandboxFaceMatchCheck> documentFaceMatchChecks,
            Integer asyncReportsDelay) {
        this.textDataChecks = textDataChecks;
        this.documentAuthenticityChecks = documentAuthenticityChecks;
        this.livenessChecks = livenessChecks;
        this.documentFaceMatchChecks = documentFaceMatchChecks;
        this.asyncReportDelay = asyncReportsDelay;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<SandboxTextDataCheck> getTextDataChecks() {
        return textDataChecks;
    }

    public List<SandboxDocumentAuthenticityCheck> getDocumentAuthenticityChecks() {
        return documentAuthenticityChecks;
    }

    public List<SandboxLivenessCheck> getLivenessChecks() {
        return livenessChecks;
    }

    public List<SandboxFaceMatchCheck> getDocumentFaceMatchChecks() {
        return documentFaceMatchChecks;
    }

    public Integer getAsyncReportDelay() {
        return asyncReportDelay;
    }

    /**
     * Builder for {@link SandboxCheckReports}
     */
    public static class Builder {

        private List<SandboxTextDataCheck> textDataCheck = new ArrayList<>();

        private List<SandboxDocumentAuthenticityCheck> documentAuthenticityCheck = new ArrayList<>();

        private List<SandboxLivenessCheck> livenessCheck = new ArrayList<>();

        private List<SandboxFaceMatchCheck> documentFaceMatchCheck = new ArrayList<>();

        private Integer asyncReportDelay;

        public Builder withTextDataCheck(SandboxTextDataCheck textDataCheckReport) {
            this.textDataCheck.add(textDataCheckReport);
            return this;
        }

        public Builder withDocumentAuthenticityCheck(SandboxDocumentAuthenticityCheck documentAuthenticityReport) {
            this.documentAuthenticityCheck.add(documentAuthenticityReport);
            return this;
        }

        public Builder withLivenessCheck(SandboxLivenessCheck livenessReport) {
            this.livenessCheck.add(livenessReport);
            return this;
        }

        public Builder withDocumentFaceMatchCheck(SandboxFaceMatchCheck documentFaceMatchReport) {
            this.documentFaceMatchCheck.add(documentFaceMatchReport);
            return this;
        }

        public Builder withAsyncReportDelay(int asyncReportDelay) {
            this.asyncReportDelay = asyncReportDelay;
            return this;
        }

        public SandboxCheckReports build() {
            return new SandboxCheckReports(textDataCheck, documentAuthenticityCheck, livenessCheck, documentFaceMatchCheck, asyncReportDelay);
        }

    }
}

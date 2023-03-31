package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = CheckResponse.class,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AuthenticityCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_AUTHENTICITY),
        @JsonSubTypes.Type(value = TextDataCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_TEXT_DATA_CHECK),
        @JsonSubTypes.Type(value = LivenessCheckResponse.class, name = DocScanConstants.LIVENESS),
        @JsonSubTypes.Type(value = FaceMatchCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_FACE_MATCH),
        @JsonSubTypes.Type(value = FaceComparisonCheckResponse.class, name = DocScanConstants.FACE_COMPARISON),
        @JsonSubTypes.Type(value = IdDocumentComparisonCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_COMPARISON),
        @JsonSubTypes.Type(value = SupplementaryDocumentTextDataCheckResponse.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_CHECK),
        @JsonSubTypes.Type(value = ThirdPartyIdentityCheckResponse.class, name = DocScanConstants.THIRD_PARTY_IDENTITY),
        @JsonSubTypes.Type(value = WatchlistScreeningCheckResponse.class, name = DocScanConstants.WATCHLIST_SCREENING),
        @JsonSubTypes.Type(value = WatchlistAdvancedCaCheckResponse.class, name = DocScanConstants.WATCHLIST_ADVANCED_CA),
        @JsonSubTypes.Type(value = IbvVisualReviewCheckResponse.class, name = DocScanConstants.IBV_VISUAL_REVIEW_CHECK),
        @JsonSubTypes.Type(value = ProfileDocumentMatchCheckResponse.class, name = DocScanConstants.PROFILE_DOCUMENT_MATCH),
        @JsonSubTypes.Type(value = DocumentSchemeValidityCheckResponse.class, name = DocScanConstants.DOCUMENT_SCHEME_VALIDITY_CHECK),
        @JsonSubTypes.Type(value = ThirdPartyIdentityFraudOneCheckResponse.class, name = DocScanConstants.THIRD_PARTY_IDENTITY_FRAUD_ONE),
        @JsonSubTypes.Type(value = SynecticsIdentityFraudCheckResponse.class, name = DocScanConstants.SYNECTICS_IDENTITY_FRAUD),
})
public class CheckResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("state")
    private String state;

    @JsonProperty("resources_used")
    private List<String> resourcesUsed;

    @JsonProperty("generated_media")
    private List<GeneratedMedia> generatedMedia;

    @JsonProperty("report")
    private ReportResponse report;

    @JsonProperty("created")
    private String created;

    @JsonProperty("last_updated")
    private String lastUpdated;

    /**
     * The ID of the check
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * The type of the check
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * The state of the check
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * The resources used by the check
     *
     * @return the resources used
     */
    public List<String> getResourcesUsed() {
        return resourcesUsed;
    }

    /**
     * The media generated by the check
     *
     * @return the generated media
     */
    public List<? extends GeneratedMedia> getGeneratedMedia() {
        return generatedMedia;
    }

    /**
     * The report associated with the check
     *
     * @return the report
     */
    public ReportResponse getReport() {
        return report;
    }

    /**
     * Date string for the created date of the check
     *
     * @return created date string
     */
    public String getCreated() {
        return created;
    }

    /**
     * Date string for the last updated date of the check
     *
     * @return last updated date string
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

}

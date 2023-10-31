package com.yoti.api.client.spi.remote.call;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

public class ProfileResponseTest {

    private static final String VALID_STRING = "abc";
    private static final byte[] VALID_CONTENT = new byte[]{ 1, 2, 3, 4, 5 };

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String sessionData = "123abc";

    /**
     * Drop JSON serialization in next major release.
     *
     * https://github.com/getyoti/yoti-java-sdk/issues/328
     */
    @Test
    @SuppressWarnings("unchecked")
    public void shouldMapToJson() {
        Receipt receipt = createReceipt();

        ProfileResponse response = new ProfileResponse.ProfileResponseBuilder()
                .setReceipt(receipt)
                .setSessionData(sessionData)
                .build();

        Map<String, Object> ProfileResponseAsMap = OBJECT_MAPPER.convertValue(response, Map.class);

        assertThat(ProfileResponseAsMap, hasEntry(Property.SESSION_DATA, sessionData));

        Map<String, Object> receiptAsMap = (Map<String, Object>) ProfileResponseAsMap.get(Property.RECEIPT);
        assertThat(receiptAsMap, hasEntry(Property.RECEIPT_ID, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.OTHER_PARTY_PROFILE_CONTENT, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.OTHER_PARTY_EXTRA_DATA_CONTENT, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.EXTRA_DATA_CONTENT, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.WRAPPED_RECEIPT_KEY, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.POLICY_URI, VALID_STRING));
        assertThat(receiptAsMap, hasEntry(Property.PERSONAL_KEY, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.SHARING_OUTCOME, Receipt.Outcome.SUCCESS.toString()));
        assertThat(receiptAsMap, hasEntry(Property.RECEIPT_ID, VALID_CONTENT));
        assertThat(receiptAsMap, hasEntry(Property.TIMESTAMP, VALID_STRING));
    }

    @Test
    public void successReceipt_shouldParseFromJson() {
        HashMap<String, Object> receiptJson = new HashMap<>();
        receiptJson.put(Property.RECEIPT_ID, VALID_CONTENT);
        receiptJson.put(Property.OTHER_PARTY_PROFILE_CONTENT, VALID_CONTENT);
        receiptJson.put(Property.OTHER_PARTY_EXTRA_DATA_CONTENT, VALID_CONTENT);
        receiptJson.put(Property.EXTRA_DATA_CONTENT, VALID_CONTENT);
        receiptJson.put(Property.WRAPPED_RECEIPT_KEY, VALID_CONTENT);
        receiptJson.put(Property.PERSONAL_KEY, VALID_CONTENT);
        receiptJson.put(Property.SHARING_OUTCOME, Receipt.Outcome.SUCCESS.toString());
        receiptJson.put(Property.POLICY_URI, VALID_STRING);
        receiptJson.put(Property.TIMESTAMP, VALID_STRING);

        HashMap<String, Object> json = new HashMap<>();
        json.put(Property.SESSION_DATA, sessionData);
        json.put(Property.RECEIPT, receiptJson);

        ProfileResponse obj = OBJECT_MAPPER.convertValue(json, ProfileResponse.class);

        assertThat(obj, notNullValue());
        assertThat(obj.getSessionData(), equalTo(sessionData));

        Receipt receipt = obj.getReceipt();
        assertThat(receipt.getReceiptId(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOtherPartyProfile(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOtherPartyExtraData(), equalTo(VALID_CONTENT));
        assertThat(receipt.getExtraData(), equalTo(VALID_CONTENT));
        assertThat(receipt.getWrappedReceiptKey(), equalTo(VALID_CONTENT));
        assertThat(receipt.getPersonalKey(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOutcome().isSuccessful(), equalTo(Receipt.Outcome.SUCCESS.isSuccessful()));
        assertThat(receipt.getPolicyUri(), equalTo(VALID_STRING));
        assertThat(receipt.getTimestamp(), equalTo(VALID_STRING));

        assertThat(obj.getError(), nullValue());
    }

    @Test
    public void failureReceipt_shouldParseFromJson() {
        HashMap<String, Object> receiptJson = new HashMap<>();
        receiptJson.put(Property.RECEIPT_ID, VALID_CONTENT);
        receiptJson.put(Property.OTHER_PARTY_PROFILE_CONTENT, VALID_CONTENT);
        receiptJson.put(Property.OTHER_PARTY_EXTRA_DATA_CONTENT, VALID_CONTENT);
        receiptJson.put(Property.EXTRA_DATA_CONTENT, VALID_CONTENT);
        receiptJson.put(Property.WRAPPED_RECEIPT_KEY, VALID_CONTENT);
        receiptJson.put(Property.PERSONAL_KEY, VALID_CONTENT);
        receiptJson.put(Property.SHARING_OUTCOME, Receipt.Outcome.FAILURE.toString());
        receiptJson.put(Property.POLICY_URI, VALID_STRING);
        receiptJson.put(Property.TIMESTAMP, VALID_STRING);

        String errorCode = "anErrorCode";
        String errorDescription = "anErrorDescription";

        HashMap<String, Object> errorJson = new HashMap<>();
        errorJson.put(Property.ERROR_CODE, errorCode);
        errorJson.put(Property.ERROR_DESCRIPTION, errorDescription);

        HashMap<String, Object> json = new HashMap<>();
        json.put(Property.SESSION_DATA, sessionData);
        json.put(Property.RECEIPT, receiptJson);
        json.put(Property.ERROR_DETAILS, errorJson);

        ProfileResponse obj = OBJECT_MAPPER.convertValue(json, ProfileResponse.class);

        assertThat(obj, notNullValue());
        assertThat(obj.getSessionData(), equalTo(sessionData));

        Receipt receipt = obj.getReceipt();
        assertThat(receipt.getReceiptId(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOtherPartyProfile(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOtherPartyExtraData(), equalTo(VALID_CONTENT));
        assertThat(receipt.getExtraData(), equalTo(VALID_CONTENT));
        assertThat(receipt.getWrappedReceiptKey(), equalTo(VALID_CONTENT));
        assertThat(receipt.getPersonalKey(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOutcome().isSuccessful(), equalTo(Receipt.Outcome.FAILURE.isSuccessful()));
        assertThat(receipt.getPolicyUri(), equalTo(VALID_STRING));
        assertThat(receipt.getTimestamp(), equalTo(VALID_STRING));

        ErrorDetails error = obj.getError();
        assertThat(error, notNullValue());
        assertThat(error.getCode(), equalTo(errorCode));
        assertThat(error.getDescription(), equalTo(errorDescription));
    }

    @Test
    public void failureReceipt_shouldParseErrorReasonFromJson() throws Exception {
        String errorCode = "anErrorCode";

        Map<String, Object> reason = new HashMap<>();
        reason.put("aReasonKey", "aReasonValue");

        HashMap<String, Object> errorJson = new HashMap<>();
        errorJson.put(Property.ERROR_CODE, errorCode);
        errorJson.put(Property.ERROR_REASON, reason);

        HashMap<String, Object> json = new HashMap<>();
        json.put(Property.ERROR_DETAILS, errorJson);

        ProfileResponse obj = OBJECT_MAPPER.convertValue(json, ProfileResponse.class);

        ErrorDetails error = obj.getError();
        assertThat(error.getCode(), equalTo(errorCode));
        assertThat(error.getReason(), equalTo(OBJECT_MAPPER.writeValueAsString(reason)));
    }

    private static Receipt createReceipt() {
        return new Receipt.Builder()
                .withProfile(VALID_CONTENT)
                .withReceiptId(VALID_CONTENT)
                .withOtherPartyExtraData(VALID_CONTENT)
                .withOtherPartyProfile(VALID_CONTENT)
                .withExtraData(VALID_CONTENT)
                .withWrappedReceiptKey(VALID_CONTENT)
                .withPersonalKey(VALID_CONTENT)
                .withRememberMeId(VALID_CONTENT)
                .withPolicyUri(VALID_STRING)
                .withTimestamp(VALID_STRING)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .build();
    }

    private static class Property {

        private static final String SESSION_DATA = "session_data";

        private static final String RECEIPT = "receipt";
        private static final String RECEIPT_ID = "receipt_id";
        private static final String OTHER_PARTY_PROFILE_CONTENT = "other_party_profile_content";
        private static final String OTHER_PARTY_EXTRA_DATA_CONTENT = "other_party_extra_data_content";
        private static final String EXTRA_DATA_CONTENT = "extra_data_content";
        private static final String WRAPPED_RECEIPT_KEY = "wrapped_receipt_key";
        private static final String POLICY_URI = "policy_uri";
        private static final String PERSONAL_KEY = "personal_key";
        private static final String SHARING_OUTCOME = "sharing_outcome";
        private static final String TIMESTAMP = "timestamp";

        private static final String ERROR_DETAILS = "error_details";
        private static final String ERROR_CODE = "error_code";
        private static final String ERROR_DESCRIPTION = "description";
        private static final String ERROR_REASON = "error_reason";

    }

}

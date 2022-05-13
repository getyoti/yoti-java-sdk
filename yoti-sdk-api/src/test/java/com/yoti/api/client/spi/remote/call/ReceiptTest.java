package com.yoti.api.client.spi.remote.call;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

public class ReceiptTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final byte[] VALID_CONTENT = new byte[]{ 1, 2, 3, 4, 5 };
    private static final String VALID_STRING = "abc";

    @Test
    public void shouldMapToJson() {
        Receipt receipt = createReceipt();

        @SuppressWarnings("unchecked")
        Map<String, Object> objectAsMap = OBJECT_MAPPER.convertValue(receipt, Map.class);
        assertThat(objectAsMap, hasEntry(Property.RECEIPT_ID, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.OTHER_PARTY_PROFILE_CONTENT, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.PROFILE_CONTENT, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.OTHER_PARTY_EXTRA_DATA_CONTENT, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.EXTRA_DATA_CONTENT, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.WRAPPED_RECEIPT_KEY, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.PERSONAL_KEY, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.REMEMBER_ME_ID, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.PARENT_REMEMBER_ME_ID, VALID_CONTENT));
        assertThat(objectAsMap, hasEntry(Property.SHARING_OUTCOME, Receipt.Outcome.SUCCESS.toString()));
        assertThat(objectAsMap, hasEntry(Property.POLICY_URI, VALID_STRING));
        assertThat(objectAsMap, hasEntry(Property.TIMESTAMP, VALID_STRING));
    }

    @Test
    public void shouldParseFromJson() {
        Map<String, Object> map = new HashMap<>();
        map.put(Property.RECEIPT_ID, VALID_CONTENT);
        map.put(Property.OTHER_PARTY_PROFILE_CONTENT, VALID_CONTENT);
        map.put(Property.PROFILE_CONTENT, VALID_CONTENT);
        map.put(Property.OTHER_PARTY_EXTRA_DATA_CONTENT, VALID_CONTENT);
        map.put(Property.EXTRA_DATA_CONTENT, VALID_CONTENT);
        map.put(Property.WRAPPED_RECEIPT_KEY, VALID_CONTENT);
        map.put(Property.PERSONAL_KEY, VALID_CONTENT);
        map.put(Property.REMEMBER_ME_ID, VALID_CONTENT);
        map.put(Property.PARENT_REMEMBER_ME_ID, VALID_CONTENT);
        map.put(Property.SHARING_OUTCOME, Receipt.Outcome.SUCCESS.toString());
        map.put(Property.POLICY_URI, VALID_STRING);
        map.put(Property.TIMESTAMP, VALID_STRING);

        Receipt receipt = OBJECT_MAPPER.convertValue(map, Receipt.class);

        assertThat(receipt, notNullValue());
        assertThat(receipt.getReceiptId(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOtherPartyProfile(), equalTo(VALID_CONTENT));
        assertThat(receipt.getProfile(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOtherPartyExtraData(), equalTo(VALID_CONTENT));
        assertThat(receipt.getExtraData(), equalTo(VALID_CONTENT));
        assertThat(receipt.getWrappedReceiptKey(), equalTo(VALID_CONTENT));
        assertThat(receipt.getPersonalKey(), equalTo(VALID_CONTENT));
        assertThat(receipt.getRememberMeId(), equalTo(VALID_CONTENT));
        assertThat(receipt.getParentRememberMeId(), equalTo(VALID_CONTENT));
        assertThat(receipt.getOutcome().toString(), equalTo(Receipt.Outcome.SUCCESS.toString()));
        assertThat(receipt.getPolicyUri(), equalTo(VALID_STRING));
        assertThat(receipt.getTimestamp(), equalTo(VALID_STRING));
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
                .withParentRememberMeId(VALID_CONTENT)
                .withOutcome(Receipt.Outcome.SUCCESS)
                .withPolicyUri(VALID_STRING)
                .withTimestamp(VALID_STRING)
                .build();
    }

    private static class Property {

        private static final String RECEIPT_ID = "receipt_id";
        private static final String OTHER_PARTY_PROFILE_CONTENT = "other_party_profile_content";
        private static final String PROFILE_CONTENT = "profile_content";
        private static final String OTHER_PARTY_EXTRA_DATA_CONTENT = "other_party_extra_data_content";
        private static final String EXTRA_DATA_CONTENT = "extra_data_content";
        private static final String WRAPPED_RECEIPT_KEY = "wrapped_receipt_key";
        private static final String POLICY_URI = "policy_uri";
        private static final String PERSONAL_KEY = "personal_key";
        private static final String REMEMBER_ME_ID = "remember_me_id";
        private static final String PARENT_REMEMBER_ME_ID = "parent_remember_me_id";
        private static final String SHARING_OUTCOME = "sharing_outcome";
        private static final String TIMESTAMP = "timestamp";

        private Property() { }

    }

}

package com.yoti.api.client.spi.remote.call;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.spi.remote.call.Receipt.Outcome;

public class ReceiptTest {
    static final byte[] VALID_CONTENT = new byte[] { 1, 2, 3, 4, 5 };
    static final String VALID_STRING = "abc";

    @Test
    public void shouldMapToJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Receipt receipt = createReceipt();

        @SuppressWarnings("unchecked")
        Map<String, Object> objectAsMap = om.convertValue(receipt, Map.class);

        assertThat(objectAsMap, hasEntry("profile_content", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("receipt_id", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("other_party_profile_content", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("other_party_extra_data_content", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("extra_data_content", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("wrapped_receipt_key", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("policy_uri", (Object) VALID_STRING));
        assertThat(objectAsMap, hasEntry("personal_key", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("sharing_outcome", (Object) Outcome.SUCCESS.toString()));
        assertThat(objectAsMap, hasEntry("remember_me_id", (Object) VALID_CONTENT));
        assertThat(objectAsMap, hasEntry("timestamp", (Object) VALID_STRING));
    }

    static Receipt createReceipt() {
        Receipt receipt = new Receipt.Builder().withProfile(VALID_CONTENT).withReceiptId(VALID_CONTENT)
                .withOtherPartyExtraData(VALID_CONTENT).withOtherPartyProfile(VALID_CONTENT)
                .withExtraData(VALID_CONTENT).withWrappedReceiptKey(VALID_CONTENT).withPolicyUri(VALID_STRING)
                .withOutcome(Outcome.SUCCESS).withPersonalKey(VALID_CONTENT)
                .withRememberMeId(VALID_CONTENT).withTimestamp(VALID_STRING).build();
        return receipt;
    }
}

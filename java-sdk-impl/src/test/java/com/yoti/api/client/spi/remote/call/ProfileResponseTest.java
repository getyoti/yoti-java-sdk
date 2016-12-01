package com.yoti.api.client.spi.remote.call;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.api.client.ActivityDetails;
import org.junit.Test;

import java.util.Map;

import static com.yoti.api.client.spi.remote.call.ReceiptTest.VALID_CONTENT;
import static com.yoti.api.client.spi.remote.call.ReceiptTest.VALID_STRING;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class ProfileResponseTest {

    private String sessionData = "123abc";
    private Receipt receipt = ReceiptTest.createReceipt();

    @Test
    @SuppressWarnings("unchecked")
    public void shouldMapToJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        ProfileResponse response = createResponse();

        Map<String, Object> objectAsMap = om.convertValue(response, Map.class);

        assertThat(objectAsMap, hasEntry("session_data", (Object) sessionData));

        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("receipt_id", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("other_party_profile_content", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("other_party_extra_data_content", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("extra_data_content", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("wrapped_receipt_key", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("policy_uri", (Object) VALID_STRING));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("personal_key", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("sharing_outcome", (Object) Receipt.Outcome.SUCCESS.toString()));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("remember_me_id", (Object) VALID_CONTENT));
        assertThat((Map<String, Object>)objectAsMap.get("receipt"), hasEntry("timestamp", (Object) VALID_STRING));
    }

    private ProfileResponse createResponse(){
        return new ProfileResponse.ProfileResponseBuilder().setReceipt(receipt).setSessionData(sessionData).createProfileResonse();
    }
}

package com.yoti.api.client.shareurl;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import com.yoti.api.client.shareurl.extension.Extension;
import com.yoti.api.client.shareurl.policy.DynamicPolicy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.*;
import org.mockito.*;

public class DynamicScenarioTest {

    private static final String SOME_ENDPOINT = "someEndpoint";

    @Mock DynamicPolicy dynamicPolicyMock;
    @Mock Extension<?> extension1Mock;
    @Mock Extension<?> extension2Mock;

    @Test
    public void shouldBuildADynamicScenario() {
        DynamicScenario result = DynamicScenario.builder()
                .withCallbackEndpoint(SOME_ENDPOINT)
                .withPolicy(dynamicPolicyMock)
                .withExtension(extension1Mock)
                .withExtension(extension2Mock)
                .build();

        assertThat(result.callbackEndpoint(), equalTo(SOME_ENDPOINT));
        assertThat(result.policy(), equalTo(dynamicPolicyMock));
        assertThat(result.extensions(), hasSize(2));
        assertThat(result.extensions(), hasItems(extension1Mock, extension2Mock));
    }

    @Test
    public void buildWithSubject() throws IOException {
        Subject subject = new Subject("A_SUBJECT_ID");

        DynamicScenario result = DynamicScenario.builder()
                .withSubject(subject)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode json = mapper.readTree(
                mapper.writeValueAsString(result.subject()).getBytes(DEFAULT_CHARSET)
        );

        assertThat(json.get("subject_id").asText(), is(Matchers.equalTo(subject.getId())));
    }

}

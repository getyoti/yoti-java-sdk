package com.yoti.api.client.qrcode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

import org.junit.Test;
import org.mockito.Mock;

public class SimpleDynamicScenarioBuilderTest {

    private static final String SOME_ENDPOINT = "someEndpoint";

    @Mock DynamicPolicy dynamicPolicyMock;
    @Mock Extension extension1Mock;
    @Mock Extension extension2Mock;

    @Test
    public void shouldBuildADynamicScenario() {
        DynamicScenario result = new SimpleDynamicScenarioBuilder()
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

}

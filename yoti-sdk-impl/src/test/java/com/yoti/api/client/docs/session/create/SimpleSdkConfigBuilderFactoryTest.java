package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import com.yoti.api.client.docs.session.create.SdkConfigBuilder;
import com.yoti.api.client.docs.session.create.SdkConfigBuilderFactory;
import com.yoti.api.client.docs.session.create.SimpleSdkConfigBuilder;
import com.yoti.api.client.docs.session.create.SimpleSdkConfigBuilderFactory;
import org.junit.Test;

public class SimpleSdkConfigBuilderFactoryTest {

    private final SdkConfigBuilderFactory testObj = new SimpleSdkConfigBuilderFactory();

    @Test
    public void shouldReturnSimpleSdkConfigBuilder() {
        SdkConfigBuilder result = testObj.create();

        assertThat(result, is(instanceOf(SimpleSdkConfigBuilder.class)));
    }

}

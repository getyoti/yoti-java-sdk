package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleSessionSpecBuilderFactoryTest {

    private final SimpleSessionSpecBuilderFactory testObj = new SimpleSessionSpecBuilderFactory();

    @Test
    public void shouldReturnSimpleSessionSpecBuilder() {
        SessionSpecBuilder result = testObj.create();

        assertThat(result, is(instanceOf(SimpleSessionSpecBuilder.class)));
    }

}

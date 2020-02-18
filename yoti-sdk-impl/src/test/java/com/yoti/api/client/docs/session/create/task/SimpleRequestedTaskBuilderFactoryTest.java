package com.yoti.api.client.docs.session.create.task;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedTaskBuilderFactoryTest {

    @Test
    public void shouldReturnSimpleRequestedTextExtractionTaskBuilder() {
        RequestedTextExtractionTaskBuilder result = new SimpleRequestedTaskBuilderFactory()
                .forTextExtractionTask();

        assertThat(result, is(instanceOf(SimpleRequestedTextExtractionTaskBuilder.class)));
    }

}

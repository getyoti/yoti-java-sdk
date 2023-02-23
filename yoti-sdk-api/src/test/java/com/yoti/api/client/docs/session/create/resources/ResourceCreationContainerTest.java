package com.yoti.api.client.docs.session.create.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceCreationContainerTest {

    @Test
    public void withApplicantProfile_shouldPopulateApplicantProfile() {
        Map<String, Object> applicantProfileInput = new HashMap<>();
        applicantProfileInput.put("someKey", "someValue");
        applicantProfileInput.put("someOtherKey", "someOtherValue");

        ResourceCreationContainer resourceCreationContainer = ResourceCreationContainer.builder()
                .withApplicantProfile(applicantProfileInput)
                .build();

        Map<String, Object> applicantProfileOutput = resourceCreationContainer.getApplicantProfile();

        assertThat(applicantProfileOutput, equalTo(applicantProfileInput));
    }

}

package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class CompanyProfilePayloadTest {

    private static final String SOME_COMPANY_NAME = "someCompanyName";

    @Test
    public void shouldBuildWithCompanyName() {
        CompanyProfilePayload result = CompanyProfilePayload.builder()
                .withCompanyName(SOME_COMPANY_NAME)
                .build();

        assertThat(result.getCompanyName(), is(SOME_COMPANY_NAME));
    }

    @Test
    public void companyName_shouldBeNullWhenNotSet() {
        CompanyProfilePayload result = CompanyProfilePayload.builder()
                .build();

        assertThat(result.getCompanyName(), is(nullValue()));
    }

}

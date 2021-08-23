package com.yoti.api.client.docs.session.instructions.branch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UkPostOfficeBranchTest {

    private static final String SOME_FAD_CODE = "someFadCode";
    private static final String SOME_NAME = "someName";
    private static final String SOME_ADDRESS = "someAddress";
    private static final String SOME_POST_CODE = "somePostCode";

    @Mock Location locationMock;

    @Test
    public void builder_shouldBuildWithFadCode() {
        UkPostOfficeBranch result = UkPostOfficeBranch.builder()
                .withFadCode(SOME_FAD_CODE)
                .build();

        assertThat(result.getType(), is("UK_POST_OFFICE"));
        assertThat(result.getFadCode(), is(SOME_FAD_CODE));
        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getAddress(), is(nullValue()));
        assertThat(result.getPostCode(), is(nullValue()));
        assertThat(result.getLocation(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithName() {
        UkPostOfficeBranch result = UkPostOfficeBranch.builder()
                .withName(SOME_NAME)
                .build();

        assertThat(result.getType(), is("UK_POST_OFFICE"));
        assertThat(result.getFadCode(), is(nullValue()));
        assertThat(result.getName(), is(SOME_NAME));
        assertThat(result.getAddress(), is(nullValue()));
        assertThat(result.getPostCode(), is(nullValue()));
        assertThat(result.getLocation(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAddress() {
        UkPostOfficeBranch result = UkPostOfficeBranch.builder()
                .withAddress(SOME_ADDRESS)
                .build();

        assertThat(result.getType(), is("UK_POST_OFFICE"));
        assertThat(result.getFadCode(), is(nullValue()));
        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getAddress(), is(SOME_ADDRESS));
        assertThat(result.getPostCode(), is(nullValue()));
        assertThat(result.getLocation(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithPostCode() {
        UkPostOfficeBranch result = UkPostOfficeBranch.builder()
                .withPostCode(SOME_POST_CODE)
                .build();

        assertThat(result.getType(), is("UK_POST_OFFICE"));
        assertThat(result.getFadCode(), is(nullValue()));
        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getAddress(), is(nullValue()));
        assertThat(result.getPostCode(), is(SOME_POST_CODE));
        assertThat(result.getLocation(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithLocation() {
        UkPostOfficeBranch result = UkPostOfficeBranch.builder()
                .withLocation(locationMock)
                .build();

        assertThat(result.getType(), is("UK_POST_OFFICE"));
        assertThat(result.getFadCode(), is(nullValue()));
        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getAddress(), is(nullValue()));
        assertThat(result.getPostCode(), is(nullValue()));
        assertThat(result.getLocation(), is(locationMock));
    }

    @Test
    public void builder_shouldBuildWithAllProperties() {
        UkPostOfficeBranch result = UkPostOfficeBranch.builder()
                .withFadCode(SOME_FAD_CODE)
                .withName(SOME_NAME)
                .withAddress(SOME_ADDRESS)
                .withPostCode(SOME_POST_CODE)
                .withLocation(locationMock)
                .build();

        assertThat(result.getType(), is("UK_POST_OFFICE"));
        assertThat(result.getFadCode(), is(SOME_FAD_CODE));
        assertThat(result.getName(), is(SOME_NAME));
        assertThat(result.getAddress(), is(SOME_ADDRESS));
        assertThat(result.getPostCode(), is(SOME_POST_CODE));
        assertThat(result.getLocation(), is(locationMock));
    }

}

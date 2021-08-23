package com.yoti.api.client.docs.session.instructions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class ContactProfileTest {

    private static final String SOME_FIRST_NAME = "someFirstName";
    private static final String SOME_LAST_NAME = "someLastName";
    private static final String SOME_EMAIL_ADDRESS = "someEmailAddress";

    @Test
    public void builder_shouldBuildWithFirstName() {
        ContactProfile result = ContactProfile.builder()
                .withFirstName(SOME_FIRST_NAME)
                .build();

        assertThat(result.getFirstName(), is(SOME_FIRST_NAME));
        assertThat(result.getLastName(), is(nullValue()));
        assertThat(result.getEmail(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithLastName() {
        ContactProfile result = ContactProfile.builder()
                .withLastName(SOME_LAST_NAME)
                .build();

        assertThat(result.getLastName(), is(SOME_LAST_NAME));
        assertThat(result.getFirstName(), is(nullValue()));
        assertThat(result.getEmail(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithEmail() {
        ContactProfile result = ContactProfile.builder()
                .withEmail(SOME_EMAIL_ADDRESS)
                .build();

        assertThat(result.getEmail(), is(SOME_EMAIL_ADDRESS));
        assertThat(result.getFirstName(), is(nullValue()));
        assertThat(result.getLastName(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithAllProperties() {
        ContactProfile result = ContactProfile.builder()
                .withFirstName(SOME_FIRST_NAME)
                .withLastName(SOME_LAST_NAME)
                .withEmail(SOME_EMAIL_ADDRESS)
                .build();

        assertThat(result.getFirstName(), is(SOME_FIRST_NAME));
        assertThat(result.getLastName(), is(SOME_LAST_NAME));
        assertThat(result.getEmail(), is(SOME_EMAIL_ADDRESS));
    }

}

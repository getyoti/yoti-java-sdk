package com.yoti.api.client.docs.session.retrieve;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.docs.session.create.SessionSpec;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

public class GetSessionResultTest {

    private static final ObjectMapper MAPPER = getMapper();

    @Test
    public void shouldReturnIdentityProfile() throws IOException {
        Map<String, Object> scheme = new HashMap<>();
        scheme.put(Property.TYPE, "A_TYPE");
        scheme.put(Property.OBJECTIVE, "AN_OBJECTIVE");

        Map<String, Object> schemesCompliance = new HashMap<>();
        schemesCompliance.put(Property.SCHEME, scheme);

        Map<String, Object> media = new HashMap<>();
        media.put(Property.ID, "A_MEDIA_ID");
        media.put(Property.TYPE, "A_TYPE");

        Map<String, Object> identityProfileReport = new HashMap<>();
        identityProfileReport.put(Property.TRUST_FRAMEWORK, "A_FRAMEWORK");
        identityProfileReport.put(Property.SCHEMES_COMPLIANCE, schemesCompliance);
        identityProfileReport.put(Property.MEDIA, media);

        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put(Property.SUBJECT_ID, "A_SUBJECT_ID");
        identityProfile.put(Property.RESULT, "A_RESULT");
        identityProfile.put(Property.IDENTITY_PROFILE_REPORT, identityProfileReport);

        Map<String, Object> session = new HashMap<>();
        session.put(Property.IDENTITY_PROFILE, identityProfile);

        GetSessionResult sessionResult = toGetSessionResult(session);

        IdentityProfileResponse sessionResultIdentityProfile = sessionResult.getIdentityProfile();

        assertThat(sessionResultIdentityProfile, is(notNullValue()));
        assertThat(
                sessionResultIdentityProfile.getSubjectId(),
                is(equalTo(identityProfile.get(Property.SUBJECT_ID)))
        );
        assertThat(
                sessionResultIdentityProfile.getResult(),
                is(equalTo(identityProfile.get(Property.RESULT)))
        );
        assertThat(sessionResultIdentityProfile.getFailureReason(), is(nullValue()));

        JsonNode jsonIdentityProfileResponse = toSessionJson(sessionResultIdentityProfile.getIdentityProfileReport());

        assertThat(jsonIdentityProfileResponse, is(notNullValue()));
        assertThat(
                jsonIdentityProfileResponse.get(Property.TRUST_FRAMEWORK).asText(),
                is(equalTo(identityProfileReport.get(Property.TRUST_FRAMEWORK)))
        );

        JsonNode schemesComplianceResponse = jsonIdentityProfileResponse.get(Property.SCHEMES_COMPLIANCE);
        assertThat(schemesComplianceResponse, is(notNullValue()));

        JsonNode schemeResponse = schemesComplianceResponse.get("scheme");
        assertThat(schemeResponse, is(notNullValue()));
        assertThat(schemeResponse.get(Property.TYPE).asText(), is(equalTo(scheme.get(Property.TYPE))));
        assertThat(schemeResponse.get(Property.OBJECTIVE).asText(), is(equalTo(scheme.get(Property.OBJECTIVE))));

        JsonNode mediaResponse = jsonIdentityProfileResponse.get(Property.MEDIA);
        assertThat(mediaResponse, is(notNullValue()));
        assertThat(mediaResponse.get(Property.ID).asText(), is(equalTo(media.get(Property.ID))));
        assertThat(mediaResponse.get(Property.TYPE).asText(), is(equalTo(media.get(Property.TYPE))));
    }

    @Test
    public void shouldReturnFailureIdentityProfile() throws IOException {
        Map<String, Object> failureReason = new HashMap<>();
        failureReason.put(Property.REASON_CODE, "A_FAILURE_REASON_CODE");

        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put(Property.SUBJECT_ID, "A_SUBJECT_ID");
        identityProfile.put(Property.RESULT, "A_RESULT");
        identityProfile.put(Property.FAILURE_REASON, failureReason);

        Map<String, Object> session = new HashMap<>();
        session.put(Property.IDENTITY_PROFILE, identityProfile);

        GetSessionResult sessionResult = toGetSessionResult(session);

        IdentityProfileResponse sessionResultIdentityProfile = sessionResult.getIdentityProfile();

        assertThat(sessionResultIdentityProfile, is(notNullValue()));
        assertThat(
                sessionResultIdentityProfile.getSubjectId(),
                is(equalTo(identityProfile.get(Property.SUBJECT_ID)))
        );
        assertThat(
                sessionResultIdentityProfile.getResult(),
                is(equalTo(identityProfile.get(Property.RESULT)))
        );

        IdentityProfileFailureResponse sessionResultFailureReason = sessionResultIdentityProfile.getFailureReason();
        assertThat(sessionResultFailureReason, is(notNullValue()));
        assertThat(
                sessionResultFailureReason.getReasonCode(),
                is(equalTo(failureReason.get(Property.REASON_CODE)))
        );
    }

    private static GetSessionResult toGetSessionResult(Object obj) {
        return MAPPER.convertValue(obj, GetSessionResult.class);
    }

    private static JsonNode toSessionJson(Object obj) throws IOException {
        SessionSpec session = SessionSpec.builder()
                .withIdentityProfile(obj)
                .build();

        return MAPPER.readTree(MAPPER.writeValueAsString(session.getIdentityProfile()).getBytes(DEFAULT_CHARSET));
    }

    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper;
    }

    private static final class Property {

        private static final String IDENTITY_PROFILE = "identity_profile";
        private static final String SUBJECT_ID = "subject_id";
        private static final String RESULT = "result";
        private static final String FAILURE_REASON = "failure_reason";
        private static final String IDENTITY_PROFILE_REPORT = "identity_profile_report";
        private static final String TRUST_FRAMEWORK = "trust_framework";
        private static final String REASON_CODE = "reason_code";
        private static final String SCHEMES_COMPLIANCE = "schemes_compliance";
        private static final String SCHEME = "scheme";
        private static final String OBJECTIVE = "objective";
        private static final String MEDIA = "media";
        private static final String ID = "id";
        private static final String TYPE = "type";

        private Property() { }

    }

}

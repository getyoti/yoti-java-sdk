package com.yoti.api.examples.springboot;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.identity.extension.Extension;
import com.yoti.api.client.identity.extension.LocationConstraintContent;
import com.yoti.api.client.identity.extension.LocationConstraintExtensionBuilder;
import com.yoti.api.client.identity.policy.Policy;
import com.yoti.api.client.identity.policy.WantedAttribute;
import com.yoti.api.client.shareurl.policy.DynamicPolicy;
import com.yoti.api.examples.springboot.data.Share;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class IdentitySessionController {

    private static final URI REDIRECT_URI = URI.create("https://localhost:8443/v2/receipt");

    private final DigitalIdentityClient client;

    @Autowired
    public IdentitySessionController(DigitalIdentityClient client) {
        this.client = client;
    }

    @GetMapping("/digital-identity-session")
    public String identityShareSession() {
        ShareSession session = client.createShareSession(
                forMinimalShare()
                // forDynamicScenarioShare()
                // forIdentityProfileShare()
                // forAdvancedIdentityProfileShare()
                // forLocationExtensionShare()
        );

        Map<String,String> response = new HashMap<>();
        response.put("sessionId", session.getId());

        return toJson(response);
    }

    private static String toJson(Map<String,String> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "error";
        }
    }

    private static ShareSessionRequest forMinimalShare() {
        return ShareSessionRequest.builder()
                .withPolicy(Policy.builder().build())
                .withRedirectUri(REDIRECT_URI)
                .build();
    }

    private static ShareSessionRequest forDynamicScenarioShare() {
        WantedAttribute givenNamesWantedAttribute = WantedAttribute.builder()
                .withName("given_names")
                .build();

        WantedAttribute emailAddressWantedAttribute = WantedAttribute.builder()
                .withName("email_address")
                .build();

        Policy policy = Policy.builder()
                .withWantedAttribute(givenNamesWantedAttribute)
                .withWantedAttribute(emailAddressWantedAttribute)
                .withFullName()
                .withSelfie()
                .withPhoneNumber()
                .withAgeOver(18)
                .build();

        return ShareSessionRequest.builder()
                .withPolicy(policy)
                .withRedirectUri(REDIRECT_URI)
                .build();
    }

    private static ShareSessionRequest forIdentityProfileShare() {
        Map<String, Object> scheme = new HashMap<>();
        scheme.put("type", "DBS");
        scheme.put("objective", "BASIC");

        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put("trust_framework", "UK_TFIDA");
        identityProfile.put("scheme", scheme);

        Policy policy = Policy.builder()
                .withWantedRememberMe(true)
                .withIdentityProfile(identityProfile)
                .build();

        return ShareSessionRequest.builder()
                .withPolicy(policy)
                .withRedirectUri(REDIRECT_URI)
                .build();
    }

    private static ShareSessionRequest forAdvancedIdentityProfileShare() {
        // UK_TFIDA
        Map<String, Object> dbsScheme = new HashMap<>();
        dbsScheme.put(Share.Policy.Profile.TYPE, "DBS");
        dbsScheme.put(Share.Policy.Profile.LABEL, "LB000");
        dbsScheme.put(Share.Policy.Profile.OBJECTIVE, "BASIC");

        Map<String, Object> rtwScheme = new HashMap<>();
        rtwScheme.put(Share.Policy.Profile.TYPE, "RTW");
        rtwScheme.put(Share.Policy.Profile.LABEL, "LB001");

        Map<String, Object> ukTfidaProfile = new HashMap<>();
        ukTfidaProfile.put(Share.Policy.Profile.TRUST_FRAMEWORK, "UK_TFIDA");
        ukTfidaProfile.put(Share.Policy.Profile.SCHEMES, toArray(dbsScheme, rtwScheme));

        // YOTI_GLOBAL
        Map<String, Object> documents = new HashMap<>();
        documents.put(Share.Policy.Profile.COUNTRY_CODES, toArray("GBR"));
        documents.put(Share.Policy.Profile.DOCUMENT_TYPES, toArray("PASSPORT", "DRIVING_LICENCE"));

        Map<String, Object> filter = new HashMap<>();
        filter.put(Share.Policy.Profile.TYPE, "DOCUMENT_RESTRICTIONS");
        filter.put(Share.Policy.Profile.INCLUSION, "INCLUDE");
        filter.put(Share.Policy.Profile.DOCUMENTS, toArray(documents));

        Map<String, Object> config = new HashMap<>();
        config.put(Share.Policy.Profile.FILTER, filter);

        Map<String, Object> identityScheme = new HashMap<>();
        identityScheme.put(Share.Policy.Profile.TYPE, "IDENTITY");
        identityScheme.put(Share.Policy.Profile.LABEL, "LB002");
        identityScheme.put(Share.Policy.Profile.OBJECTIVE, "AL_L1");
        identityScheme.put(Share.Policy.Profile.CONFIG, config);

        Map<String, Object> yotiGlobalProfile = new HashMap<>();
        yotiGlobalProfile.put(Share.Policy.Profile.TRUST_FRAMEWORK, "YOTI_GLOBAL");
        yotiGlobalProfile.put(Share.Policy.Profile.SCHEMES, toArray(identityScheme));

        Map<String, Object> advancedIdentityProfile = new HashMap<>();
        advancedIdentityProfile.put(Share.Policy.Profile.PROFILES, toArray(ukTfidaProfile, yotiGlobalProfile));

        Map<String, Object> subject = new HashMap<>();
        subject.put(Share.Subject.SUBJECT_ID, "00000000-1111-2222-3333-444444444444");

        Policy policy = Policy.builder()
                .withAdvancedIdentityProfile(advancedIdentityProfile)
                .build();

        return ShareSessionRequest.builder()
                .withPolicy(policy)
                .withSubject(subject)
                .withRedirectUri(REDIRECT_URI)
                .build();
    }

    private static List<Map<String, Object>> toArray(Map<String, Object>... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    private static List<String> toArray(String... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    private static ShareSessionRequest forLocationExtensionShare() {
        Extension<LocationConstraintContent> locationExtension = new LocationConstraintExtensionBuilder()
                .withLatitude(51.5074)
                .withLongitude(-0.1278)
                .withRadius(6000)
                .build();

        return ShareSessionRequest.builder()
                .withPolicy(Policy.builder().withWantedRememberMe(true).build())
                .withExtension(locationExtension)
                .withRedirectUri(REDIRECT_URI)
                .build();
    }

}

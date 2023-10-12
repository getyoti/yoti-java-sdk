package com.yoti.api.examples.springboot;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.identity.extension.Extension;
import com.yoti.api.client.identity.extension.LocationConstraintContent;
import com.yoti.api.client.identity.extension.LocationConstraintExtensionBuilder;
import com.yoti.api.client.identity.policy.Policy;
import com.yoti.api.client.identity.policy.WantedAttribute;

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
                // forMinimalShare()
                forDynamicScenarioShare()
                // forIdentityProfileShare()
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

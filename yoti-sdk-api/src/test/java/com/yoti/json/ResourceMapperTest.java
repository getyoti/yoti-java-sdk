package com.yoti.json;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.identity.ShareSessionNotification;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.identity.constraint.SourceConstraint;
import com.yoti.api.client.identity.extension.Extension;
import com.yoti.api.client.identity.extension.ThirdPartyAttributeContent;
import com.yoti.api.client.identity.extension.ThirdPartyAttributeExtensionBuilder;
import com.yoti.api.client.identity.policy.Policy;
import com.yoti.api.client.identity.policy.WantedAnchor;
import com.yoti.api.client.identity.policy.WantedAttribute;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

public class ResourceMapperTest {

    @Test
    public void mapper_CreatesValidShareSessionRequestJson() throws Exception {
        String subjectId = "subject_id";
        String subjectIdValue = "00000000-1111-2222-3333-444444444444";

        Map<String, Object> subject = new HashMap<>();
        subject.put(subjectId, subjectIdValue);

        String wantedAnchorValue = "aAnchorValue";
        String wantedAnchorSubType = "aAnchorSubType";
        SourceConstraint constraint = SourceConstraint.builder()
                .withWantedAnchor(
                        WantedAnchor.builder()
                                .withValue(wantedAnchorValue)
                                .withSubType(wantedAnchorSubType)
                                .build()
                )
                .withSoftPreference(true)
                .build();

        String wantedAttributeName = "aWantedAttributeName";
        String wantedAttributeDerivation = "aWantedAttributeDerivation";

        String trustFramework = "trust_framework";
        String trustFrameworkValue = "aTrustFramework";
        String scheme = "scheme";
        String schemeValue = "aScheme";
        Map<String, Object> identityProfile = new HashMap<>();
        identityProfile.put(trustFramework, trustFrameworkValue);
        identityProfile.put(scheme, schemeValue);

        Policy policy = Policy.builder()
                .withWantedAttribute(
                        WantedAttribute.builder()
                                .withName(wantedAttributeName)
                                .withDerivation(wantedAttributeDerivation)
                                .withOptional(true)
                                .withAcceptSelfAsserted(true)
                                .withConstraint(constraint)
                                .build()
                )
                .withWantedAuthType(1)
                .withWantedRememberMe(true)
                .withWantedRememberMeOptional(true)
                .withIdentityProfile(identityProfile)
                .build();

        String thirdPartyAttributeDefinition = "aDefinition";
        Extension<ThirdPartyAttributeContent> extension = new ThirdPartyAttributeExtensionBuilder()
                .withDefinition(thirdPartyAttributeDefinition)
                .withExpiryDate(OffsetDateTime.now())
                .build();

        String redirectUriValue = "aRedirectUri";
        String notificationUriValue = "aNotificationUri";
        ShareSessionRequest request = ShareSessionRequest.builder()
                .withSubject(subject)
                .withPolicy(policy)
                .withExtension(extension)
                .withRedirectUri(new URI(redirectUriValue))
                .withNotification(ShareSessionNotification.builder(new URI(notificationUriValue)).build())
                .build();

        byte[] payload = ResourceMapper.writeValueAsString(request);

        JsonNode json = new ObjectMapper().readTree(new String(payload));

        JsonNode subjectNode = json.get("subject");
        assertThat(subjectNode.get(subjectId).asText(), equalTo(subjectIdValue));

        JsonNode policyNode = json.get("policy");

        JsonNode policyWantedNode = policyNode.get("wanted");
        assertThat(policyWantedNode.size(), equalTo(1));

        JsonNode wantedAttributeNode = policyWantedNode.get(0);
        assertThat(wantedAttributeNode.get("name").asText(), equalTo(wantedAttributeName));
        assertThat(wantedAttributeNode.get("derivation").asText(), equalTo(wantedAttributeDerivation));
        assertTrue(wantedAttributeNode.get("optional").asBoolean());
        assertTrue(wantedAttributeNode.get("accept_self_asserted").asBoolean());

        JsonNode wantedAttributeConstraintsNode = wantedAttributeNode.get("constraints");
        assertThat(wantedAttributeConstraintsNode.size(), equalTo(1));

        JsonNode wantedAttributeConstraintNode = wantedAttributeConstraintsNode.get(0);
        assertThat(wantedAttributeConstraintNode.get("type").asText(), equalTo("SOURCE"));
        JsonNode preferredSourcesNode = wantedAttributeConstraintNode.get("preferred_sources");
        assertTrue(preferredSourcesNode.get("soft_preference").asBoolean());

        JsonNode preferredSourceAnchorsNode = preferredSourcesNode.get("anchors");
        assertThat(preferredSourceAnchorsNode.size(), equalTo(1));

        JsonNode preferredSourceAnchorNode = preferredSourceAnchorsNode.get(0);
        assertThat(preferredSourceAnchorNode.get("name").asText(), equalTo(wantedAnchorValue));
        assertThat(preferredSourceAnchorNode.get("sub_type").asText(), equalTo(wantedAnchorSubType));

        JsonNode wantedAuthTypes = policyNode.get("wanted_auth_types");
        assertThat(wantedAuthTypes.size(), equalTo(1));
        assertThat(wantedAuthTypes.get(0).asInt(), equalTo(1));

        assertTrue(policyNode.get("wanted_remember_me").asBoolean());
        assertTrue(policyNode.get("wanted_remember_me_optional").asBoolean());

        JsonNode wantedAttributeIdentityProfile = policyNode.get("identity_profile_requirements");
        assertThat(wantedAttributeIdentityProfile.get(trustFramework).asText(), equalTo(trustFrameworkValue));
        assertThat(wantedAttributeIdentityProfile.get(scheme).asText(), equalTo(schemeValue));

        JsonNode extensionsNode = json.get("extensions");
        assertThat(extensionsNode.size(), equalTo(1));

        JsonNode extensionNode = extensionsNode.get(0);
        assertThat(extensionNode.get("type").asText(), equalTo("THIRD_PARTY_ATTRIBUTE"));

        JsonNode extensionContent = extensionNode.get("content");
        assertThat(extensionContent.get("expiry_date").asText(), notNullValue());

        JsonNode extensionDefinitions = extensionContent.get("definitions");
        assertThat(extensionDefinitions.size(), equalTo(1));

        JsonNode extensionDefinition = extensionDefinitions.get(0);
        assertThat(extensionDefinition.get("name").asText(), equalTo(thirdPartyAttributeDefinition));

        assertThat(json.get("redirectUri").asText(), equalTo(redirectUriValue));

        JsonNode notificationNode = json.get("notification");
        assertThat(notificationNode.get("url").asText(), equalTo(notificationUriValue));
        assertThat(notificationNode.get("method").asText(), equalTo("POST"));
        assertTrue(notificationNode.get("verifyTls").asBoolean());
        assertThat(notificationNode.get("headers").size(), equalTo(0));
    }

}

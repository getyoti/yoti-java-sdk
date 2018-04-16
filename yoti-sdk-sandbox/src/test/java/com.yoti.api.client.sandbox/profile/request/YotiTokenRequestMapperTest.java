package com.yoti.api.client.sandbox.profile.request;

import static com.yoti.api.client.sandbox.profile.request.TestData.AGE_OVER_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.AGE_UNDER_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.DATE_OF_BIRTH_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.EMAIL_ADDRESS;
import static com.yoti.api.client.sandbox.profile.request.TestData.EMAIL_ADDRESS_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.FAMILY_NAME;
import static com.yoti.api.client.sandbox.profile.request.TestData.FAMILY_NAME_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.FULL_NAME;
import static com.yoti.api.client.sandbox.profile.request.TestData.FULL_NAME_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.GENDER_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.GIVEN_NAMES;
import static com.yoti.api.client.sandbox.profile.request.TestData.GIVEN_NAMES_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.NATIONALITY;
import static com.yoti.api.client.sandbox.profile.request.TestData.NATIONALITY_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.PHONE_NUMBER;
import static com.yoti.api.client.sandbox.profile.request.TestData.PHONE_NUMBER_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.POSTAL_ADDRESS;
import static com.yoti.api.client.sandbox.profile.request.TestData.POSTAL_ADDRESS_ATTRIBUTE;
import static com.yoti.api.client.sandbox.profile.request.TestData.SELFIE;
import static com.yoti.api.client.sandbox.profile.request.TestData.SELFIE_ATTRIBUTE;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import java.util.Collections;
import java.util.List;

import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.sandbox.profile.SandboxHumanProfile;
import com.yoti.api.client.sandbox.profile.SandboxHumanProfileBuilder;
import com.yoti.api.client.sandbox.profile.request.attribute.Attribute;
import com.yoti.api.client.sandbox.profile.request.attribute.SandboxDateAttribute;
import com.yoti.api.client.sandbox.profile.request.attribute.derivation.SupportedAgeDerivation;
import com.yoti.api.client.sandbox.profile.request.attribute.derivation.AgeVerification;

import org.junit.*;

public class YotiTokenRequestMapperTest {


    @Test
    public void testMapSandboxHumanProfileFamilyName(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .familyName(FAMILY_NAME).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");

        match(yotiTokenRequest.getAttributes(), Collections.singletonList(FAMILY_NAME_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileGivenNames(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .givenNames(GIVEN_NAMES).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");

        match(yotiTokenRequest.getAttributes(), Collections.singletonList(GIVEN_NAMES_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileFullName(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .fullName(FULL_NAME).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");

        match(yotiTokenRequest.getAttributes(), Collections.singletonList(FULL_NAME_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileDateOfBirth() {
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .dateOfBirth(new SandboxDateAttribute(1902, 2, 2)).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");

        match(yotiTokenRequest.getAttributes(), Collections.singletonList(DATE_OF_BIRTH_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileGender(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .gender(HumanProfile.Gender.MALE).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(GENDER_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileAgeUnderVerification(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .ageVerification( new AgeVerification(new SandboxDateAttribute(2009, 2, 2),
                        SupportedAgeDerivation.AGE_UNDER, "18")).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(AGE_UNDER_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileAgeOverVerification(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .ageVerification( new AgeVerification(new SandboxDateAttribute(1978, 2, 2),
                        SupportedAgeDerivation.AGE_OVER, "18")).build();
        YotiTokenRequest sharingTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(sharingTokenRequest.getAttributes(), Collections.singletonList(AGE_OVER_ATTRIBUTE));
    }



    @Test
    public void testMapSandboxHumanProfilePostalAddress(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .postalAddress(POSTAL_ADDRESS).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(POSTAL_ADDRESS_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileNationality(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .nationality(NATIONALITY).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(NATIONALITY_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfilePhoneNumber(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .phoneNumber(PHONE_NUMBER).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(PHONE_NUMBER_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileEmailAddress(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .emailAddress(EMAIL_ADDRESS).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(EMAIL_ADDRESS_ATTRIBUTE));
    }

    @Test
    public void testMapSandboxHumanProfileSelfie(){
        SandboxHumanProfile sandboxHumanProfile = SandboxHumanProfileBuilder.newInstance()
                .selfie(SELFIE.getBytes()).build();
        YotiTokenRequest yotiTokenRequest = YotiTokenRequestMapper.mapSharingTokenRequest(sandboxHumanProfile, "aRandomId");
        match(yotiTokenRequest.getAttributes(), Collections.singletonList(SELFIE_ATTRIBUTE));
    }

    private void match(List<Attribute> producedAttributeList, List<Attribute> expectedAttributeList){
        assertThat(producedAttributeList, hasSize(expectedAttributeList.size()));
        for (Attribute anExpectedAttribute : expectedAttributeList) {
            assertThat(producedAttributeList, containsInAnyOrder(anExpectedAttribute));
        }
    }

}

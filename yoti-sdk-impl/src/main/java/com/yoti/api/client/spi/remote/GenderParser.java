package com.yoti.api.client.spi.remote;

import com.yoti.api.client.HumanProfile;

public class GenderParser {

    public HumanProfile.Gender parseFrom(String genderString) {
        if (genderString != null) {
            try {
                return HumanProfile.Gender.valueOf(genderString);
            } catch (IllegalArgumentException isa) {
                return HumanProfile.Gender.OTHER;
            }
        } else {
            return null;
        }
    }

}

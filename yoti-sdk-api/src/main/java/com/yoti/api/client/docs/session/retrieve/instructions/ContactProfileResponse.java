package com.yoti.api.client.docs.session.retrieve.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactProfileResponse {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}

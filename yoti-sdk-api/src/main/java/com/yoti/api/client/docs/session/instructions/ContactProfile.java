package com.yoti.api.client.docs.session.instructions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactProfile {

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("email")
    private final String email;

    private ContactProfile(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static ContactProfile.Builder builder() {
        return new ContactProfile.Builder();
    }

    /**
     * The first name set as part of the contact profile
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The last name set as part of the contact profile
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The email address set as part of the contact profile
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private String email;

        private Builder() {}

        /**
         * Sets the first name that will be used as part of the contact profile
         *
         * @param firstName the first name
         * @return the builder
         */
        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Sets the last name that will be used as part of the contact profile
         *
         * @param lastName the last name
         * @return the builder
         */
        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Sets the email address that will be used as part of the contact profile
         *
         * @param email the email address
         * @return the builder
         */
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ContactProfile build() {
            return new ContactProfile(firstName, lastName, email);
        }

    }

}

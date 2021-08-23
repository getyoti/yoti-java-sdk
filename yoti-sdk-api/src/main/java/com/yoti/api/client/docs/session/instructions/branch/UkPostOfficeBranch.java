package com.yoti.api.client.docs.session.instructions.branch;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UkPostOfficeBranch extends Branch {

    @JsonProperty("fad_code")
    private final String fadCode;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("address")
    private final String address;

    @JsonProperty("post_code")
    private final String postCode;

    @JsonProperty("location")
    private final Location location;

    private UkPostOfficeBranch(String fadCode,
            String name,
            String address,
            String postCode,
            Location location) {
        super(DocScanConstants.UK_POST_OFFICE);
        this.fadCode = fadCode;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.location = location;
    }

    public static UkPostOfficeBranch.Builder builder() {
        return new UkPostOfficeBranch.Builder();
    }

    /**
     * Returns the FAD code that has been set for the {@link UkPostOfficeBranch}
     *
     * @return the FAD code
     */
    public String getFadCode() {
        return fadCode;
    }

    /**
     * Returns the name that has been set for the {@link UkPostOfficeBranch}
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the address that has been set for the {@link UkPostOfficeBranch}
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the post code that has been set for the {@link UkPostOfficeBranch}
     *
     * @return the post code
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Returns the {@link Location} that has been set for the {@link UkPostOfficeBranch}
     *
     * @return the FAD code
     */
    public Location getLocation() {
        return location;
    }

    public static class Builder {

        private String fadCode;
        private String name;
        private String address;
        private String postCode;
        private Location location;

        private Builder() {}

        public Builder withFadCode(String fadCode) {
            this.fadCode = fadCode;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public UkPostOfficeBranch build() {
            return new UkPostOfficeBranch(fadCode, name, address, postCode, location);
        }

    }

}

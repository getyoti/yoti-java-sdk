package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPrice {

    @JsonProperty("amount")
    private final String amount;

    @JsonProperty("currency")
    private final String currency;

    private UserPrice(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static UserPrice.Builder builder() {
        return new UserPrice.Builder();
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public static class Builder {

        private String amount;
        private String currency;

        private Builder() { }

        /**
         * Sets the amount
         *
         * @param amount the amount
         * @return the builder
         */
        public Builder withAmount(String amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the currency
         *
         * @param currency the currency
         * @return the builder
         */
        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Sets the amount = GBP
         *
         * @return the builder
         */
        public Builder withGbpCurrency() {
            return withCurrency(DocScanConstants.GBP);
        }

        /**
         * Builds the {@link UserPrice} based on the values supplied to the builder
         *
         * @return the built {@link UserPrice}
         */
        public UserPrice build() {
            return new UserPrice(amount, currency);
        }

    }

}

package com.yoti.api.client.docs.session.create.check.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedFuzzyMatchingStrategy extends RequestedCaMatchingStrategy {

    @JsonProperty("fuzziness")
    private final Double fuzziness;

    private RequestedFuzzyMatchingStrategy(Double fuzziness) {
        this.fuzziness = fuzziness;
    }

    public static RequestedFuzzyMatchingStrategy.Builder builder() {
        return new RequestedFuzzyMatchingStrategy.Builder();
    }

    public Double getFuzziness() {
        return fuzziness;
    }

    public static class Builder {

        private Double fuzziness;

        private Builder() {}

        public Builder withFuzziness(double fuzziness) {
            this.fuzziness = fuzziness;
            return this;
        }

        public RequestedFuzzyMatchingStrategy build() {
            return new RequestedFuzzyMatchingStrategy(fuzziness);
        }
    }
}

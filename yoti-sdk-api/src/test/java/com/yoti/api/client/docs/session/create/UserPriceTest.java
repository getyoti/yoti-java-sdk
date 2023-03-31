package com.yoti.api.client.docs.session.create;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class UserPriceTest {

    private final static String SOME_CURRENCY = "someCurrency";
    private final static String SOME_AMOUNT = "someAmount";

    @Test
    public void builder_withGbpCurrency_shouldSetCorrectCurrencyValue() {
        UserPrice result = UserPrice.builder()
                .withGbpCurrency()
                .build();

        assertThat(result.getCurrency(), is("GBP"));
    }

    @Test
    public void builder_shouldSetCorrectCurrencyValue() {
        UserPrice result = UserPrice.builder()
                .withCurrency(SOME_CURRENCY)
                .build();

        assertThat(result.getCurrency(), is(SOME_CURRENCY));
    }

    @Test
    public void builder_shouldSetCorrectAmountValue() {
        UserPrice result = UserPrice.builder()
                .withAmount(SOME_AMOUNT)
                .build();

        assertThat(result.getAmount(), is(SOME_AMOUNT));
    }

}

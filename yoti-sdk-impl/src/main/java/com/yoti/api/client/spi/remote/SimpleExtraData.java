package com.yoti.api.client.spi.remote;

import com.yoti.api.client.AttributeIssuanceDetails;
import com.yoti.api.client.ExtraData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

public class SimpleExtraData implements ExtraData {

    private AttributeIssuanceDetails attributeIssuanceDetails;

    public SimpleExtraData() {
        this(Collections.emptyList());
    }

    public SimpleExtraData(List<Object> dataEntries) {
        notNull(dataEntries, "dataEntries");

        List<AttributeIssuanceDetails> attributeIssuanceDetailsList = filterForType(dataEntries, AttributeIssuanceDetails.class);
        attributeIssuanceDetails = attributeIssuanceDetailsList.size() > 0 ? attributeIssuanceDetailsList.get(0) : null;
    }

    /**
     * Return the credential issuance details associated with the extra
     * data in a receipt.
     *
     * @return the credential issuance details, null if not available
     */
    @Override
    public AttributeIssuanceDetails getAttributeIssuanceDetails() {
        return attributeIssuanceDetails;
    }

    private <T> List<T> filterForType(List<Object> values, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (Object value : values) {
            if (clazz.isInstance(value)) {
                list.add((T) value);
            }
        }
        return Collections.unmodifiableList(list);
    }
}

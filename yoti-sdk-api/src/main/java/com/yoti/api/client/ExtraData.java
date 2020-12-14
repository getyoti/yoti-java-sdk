package com.yoti.api.client;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtraData {

    private final AttributeIssuanceDetails attributeIssuanceDetails;

    public ExtraData() {
        this(Collections.emptyList());
    }

    public ExtraData(List<Object> dataEntries) {
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
    public AttributeIssuanceDetails getAttributeIssuanceDetails() {
        return attributeIssuanceDetails;
    }

    @SuppressWarnings("unchecked")
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

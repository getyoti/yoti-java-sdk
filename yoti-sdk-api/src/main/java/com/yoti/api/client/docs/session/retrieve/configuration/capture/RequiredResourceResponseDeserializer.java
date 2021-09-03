package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import static com.yoti.api.client.docs.DocScanConstants.FACE_CAPTURE;
import static com.yoti.api.client.docs.DocScanConstants.ID_DOCUMENT;
import static com.yoti.api.client.docs.DocScanConstants.LIVENESS;
import static com.yoti.api.client.docs.DocScanConstants.SUPPLEMENTARY_DOCUMENT;

import java.io.IOException;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredIdDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredSupplementaryDocumentResourceResponse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class RequiredResourceResponseDeserializer extends JsonDeserializer<RequiredResourceResponse> {

    @Override
    public RequiredResourceResponse deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        final JsonNode node = p.getCodec().readTree(p);
        final ObjectMapper mapper = (ObjectMapper) p.getCodec();

        if (node.has("type")) {
            final String type = node.get("type").asText();
            switch (type) {
                case ID_DOCUMENT:
                    return mapper.treeToValue(node, RequiredIdDocumentResourceResponse.class);
                case SUPPLEMENTARY_DOCUMENT:
                    return mapper.treeToValue(node, RequiredSupplementaryDocumentResourceResponse.class);
                case LIVENESS:
                    return mapper.treeToValue(node, RequiredLivenessResourceResponse.class);
                case FACE_CAPTURE:
                    return mapper.treeToValue(node, RequiredFaceCaptureResourceResponse.class);
                default:
                    return mapper.treeToValue(node, RequiredResourceResponse.class);
            }
        }

        return null;
    }
}

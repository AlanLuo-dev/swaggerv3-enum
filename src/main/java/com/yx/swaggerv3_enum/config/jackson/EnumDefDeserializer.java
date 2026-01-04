package com.yx.swaggerv3_enum.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.yx.swaggerv3_enum.config.core.EnumDef;

import java.io.IOException;
import java.util.Objects;


public class EnumDefDeserializer
        extends JsonDeserializer<Enum<?>> {

    private final Class<? extends Enum<?>> enumType;

    public EnumDefDeserializer(Class<? extends Enum<?>> enumType) {
        this.enumType = enumType;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        JsonNode node = p.getCodec().readTree(p);
        String input;

        // {"value": "red"}
        if (node.isObject() && node.has("value")) {
            input = node.get("value").asText();
        }
        // "red"
        else if (node.isValueNode()) {
            input = node.asText();
        } else {
            return null;
        }

        for (Enum<?> e : enumType.getEnumConstants()) {
            EnumDef schema = (EnumDef) e;
            if (Objects.equals(schema.getValue(), input)) {
                return e;
            }
        }

        return null;
    }
}

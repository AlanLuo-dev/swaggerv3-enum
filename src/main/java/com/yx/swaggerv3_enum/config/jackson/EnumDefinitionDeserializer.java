package com.yx.swaggerv3_enum.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.yx.swaggerv3_enum.config.core.EnumDefinition;

import java.io.IOException;
import java.util.Objects;


public class EnumDefinitionDeserializer
        extends JsonDeserializer<Enum<?>> {

    private final Class<? extends Enum<?>> enumType;

    public EnumDefinitionDeserializer(Class<? extends Enum<?>> enumType) {
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
            EnumDefinition schema = (EnumDefinition) e;
            if (Objects.equals(schema.getValue(), input)) {
                return e;
            }
        }

        return null;
    }
}

package com.yx.swaggerv3_enum.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.yx.swaggerv3_enum.config.core.EnumDef;

import java.io.IOException;


public class EnumDefDeserializer extends JsonDeserializer<Enum<?>> {

    private final Class<? extends Enum<?>> enumType;

    public EnumDefDeserializer(Class<? extends Enum<?>> enumType) {
        this.enumType = enumType;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        JsonNode node = p.getCodec().readTree(p);
        Object inputValue;


        if (node.isObject() && node.has("value")) {
            JsonNode valueNode = node.get("value");
            inputValue = readNodeValue(valueNode);
        }
        // 480 / "red"
        else if (node.isValueNode()) {
            inputValue = readNodeValue(node);
        } else {
            return null;
        }

        for (Enum<?> e : enumType.getEnumConstants()) {
            EnumDef schema = (EnumDef) e;
            Object enumValue = schema.getValue();

            if (enumValue != null
                    && enumValue.toString().equals(inputValue.toString())) {
                return e;
            }
        }

        return null;
    }

    private Object readNodeValue(JsonNode node) {
        if (node.isInt()) {
            return node.intValue();
        }
        if (node.isLong()) {
            return node.longValue();
        }
        if (node.isTextual()) {
            return node.textValue();
        }
        if (node.isBoolean()) {
            return node.booleanValue();
        }
        if (node.isNumber()) {
            return node.numberValue();
        }
        return node.asText();
    }
}

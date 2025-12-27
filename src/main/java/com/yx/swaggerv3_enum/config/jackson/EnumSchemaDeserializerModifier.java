package com.yx.swaggerv3_enum.config.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.yx.swaggerv3_enum.config.core.EnumSchema;

public class EnumSchemaDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyEnumDeserializer(
            DeserializationConfig config,
            JavaType type,
            BeanDescription beanDesc,
            JsonDeserializer<?> deserializer) {

        Class<?> rawClass = type.getRawClass();

        if (rawClass.isEnum() && EnumSchema.class.isAssignableFrom(rawClass)) {
            return new EnumSchemaDeserializer(
                    (Class<? extends Enum<?>>) rawClass
            );
        }

        return deserializer;
    }
}


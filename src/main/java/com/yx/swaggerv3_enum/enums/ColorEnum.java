package com.yx.swaggerv3_enum.enums;

import com.yx.swaggerv3_enum.config.core.EnumSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumSchema<String, ColorEnum> {

    HONG_SE("red", "红色"),
    LAN_SE("blue", "蓝色");

    private final String value;
    private final String label;

}

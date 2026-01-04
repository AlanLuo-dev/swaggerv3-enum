package com.yx.swaggerv3_enum.enums;

import com.yx.swaggerv3_enum.config.core.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumDefinition<String, ColorEnum> {

    HONG_SE("red", "红色"),
    LAN_SE("blue", "蓝色");

    private final String value;
    private final String label;

}

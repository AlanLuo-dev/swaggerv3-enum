package com.yx.swaggerv3_enum.enums;

import com.yx.swaggerv3_enum.config.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumDef<String, ColorEnum> {

    HONG_SE("red", "红色"),
    LAN_SE("blue", "蓝色"),
    HEI_SE("black", "黑色");

    private final String value;
    private final String label;

    @Override
    public String getEnumName() {
        return "颜色";
    }
}

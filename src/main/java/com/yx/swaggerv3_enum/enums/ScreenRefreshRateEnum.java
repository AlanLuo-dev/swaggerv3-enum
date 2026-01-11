package com.yx.swaggerv3_enum.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 屏幕刷新率枚举类（仅包含value、label两个字段）
 * value：刷新率数值（Hz）
 * label：刷新率对应的中文标签
 */
@Getter
@RequiredArgsConstructor
public enum ScreenRefreshRateEnum {
    // 定义枚举实例：仅绑定value（int类型）和label（中文标签）
    REFRESH_60HZ(60, "60赫兹"),
    REFRESH_90HZ(90, "90赫兹"),
    REFRESH_120HZ(120, "120赫兹"),
    REFRESH_144HZ(144, "144赫兹"),
    REFRESH_165HZ(165, "165赫兹"),
    REFRESH_240HZ(240, "240赫兹"),
    REFRESH_360HZ(360, "360赫兹");

    // 1. 核心字段1：value（刷新率数值，int类型，满足场景需求）
    private final int value;
    // 2. 核心字段2：label（中文标签，描述刷新率）
    private final String label;
}

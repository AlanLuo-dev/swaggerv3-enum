package com.yx.swaggerv3_enum.enums;

import com.yx.swaggerv3_enum.config.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 视频分辨率枚举（3-4位数字，追剧/刷视频必见）
@Getter
@RequiredArgsConstructor
public enum VideoResolutionEnum implements EnumDef<Integer, VideoResolutionEnum> {
    SD_480P(480, "标清480P"),
    HD_720P(720, "高清720P"),
    FHD_1080P(1080, "全高清1080P"),
    UHD_2160P(2160, "4K超清2160P");

    private final Integer value;
    private final String label;
}

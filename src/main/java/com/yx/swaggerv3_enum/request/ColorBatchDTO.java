package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "包含 枚举数组的JSON对象")
public class ColorBatchDTO {

    @Schema(description = "颜色列表")
    private List<ColorEnum> colors;

    @Schema(description = "操作人")
    private String operator;
}

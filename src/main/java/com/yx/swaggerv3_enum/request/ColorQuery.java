package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "颜色查询参数")
public class ColorQuery {

    @Schema(description = "颜色")
    private ColorEnum color;

    @Schema(description = "备注")
    private String remark;
}


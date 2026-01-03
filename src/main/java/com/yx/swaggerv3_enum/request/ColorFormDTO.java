package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "颜色表单对象")
public class ColorFormDTO {

    @Schema(description = "颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    private ColorEnum color;

    @Schema(description = "备注")
    private String note;
}

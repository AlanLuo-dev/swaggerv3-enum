package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "颜色表单对象")
public class ColorFormDTO {

    @Schema(description = "_（入参）颜色")
    @NotNull
    private ColorEnum color;

    @Schema(description = "备注")
    private String note;
}

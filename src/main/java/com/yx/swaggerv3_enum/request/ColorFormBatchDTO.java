package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "颜色表单对象")
public class ColorFormBatchDTO {

    @Schema(description = "颜色数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ColorEnum> colorList;
}

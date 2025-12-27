package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "颜色提交对象")
public class ColorDTO {

    @Schema(description = "颜色")
    private ColorEnum color;

    @Schema(description = "用户名")
    private String username;
}

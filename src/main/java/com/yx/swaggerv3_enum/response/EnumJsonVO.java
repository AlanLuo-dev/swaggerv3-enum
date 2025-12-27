package com.yx.swaggerv3_enum.response;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumJsonVO {

    @Schema(description = "参数")
    private ColorEnum color;

    @Schema(description = "用户名")
    private String username;
}

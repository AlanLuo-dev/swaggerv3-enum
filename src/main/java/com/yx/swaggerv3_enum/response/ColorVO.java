package com.yx.swaggerv3_enum.response;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "返回数据")
public class ColorVO {

    @Schema(description = "返回参数：颜色枚举对象")
    private ColorEnum color;

    @Schema(description = "返回参数：用户名")
    private String username;
}

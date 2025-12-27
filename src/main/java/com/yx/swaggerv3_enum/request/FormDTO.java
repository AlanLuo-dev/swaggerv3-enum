package com.yx.swaggerv3_enum.request;


import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户表单参数")
public class FormDTO {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "测试枚举参数")
    private ColorEnum colorEnum;
}

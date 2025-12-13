package com.yx.swaggerv3_enum.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UrlQuery {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "年龄")
    private Integer age;
}

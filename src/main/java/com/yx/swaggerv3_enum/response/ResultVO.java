package com.yx.swaggerv3_enum.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

    @Schema(description = "返回的数据")
    private T data;
}

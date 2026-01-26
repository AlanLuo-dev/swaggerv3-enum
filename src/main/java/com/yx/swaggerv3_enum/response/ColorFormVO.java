package com.yx.swaggerv3_enum.response;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorFormVO {

    @Schema(description = "_（返回参数）颜色")
    private ColorEnum color;

    @Schema(description = "备注")
    private String note;
}

package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColorBatchQuery {

    @Schema(description = "颜色数组")
    private List<ColorEnum> colorList;
}


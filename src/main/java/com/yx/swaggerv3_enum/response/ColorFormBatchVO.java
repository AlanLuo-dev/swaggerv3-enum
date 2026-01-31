package com.yx.swaggerv3_enum.response;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.enums.VideoResolutionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "表单批量返回（ColorFormBatchVO类的注释）")
public class ColorFormBatchVO {

    @Schema(description = "表单返回参数: 颜色数组")
    private List<@NotNull ColorEnum> colorList;

    @Schema(description = "表单返回参数: 视频分辨率数组")
    private List<@NotNull VideoResolutionEnum> videoResolutionList;
}

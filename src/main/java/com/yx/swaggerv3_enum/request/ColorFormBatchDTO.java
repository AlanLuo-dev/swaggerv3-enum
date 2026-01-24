package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.enums.VideoResolutionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "颜色表单对象")
public class ColorFormBatchDTO {

    @Schema(description = "颜色数组")
    @NotEmpty(message = "颜色不能为空数组")
    private List<@NotNull ColorEnum> colorList;

    @Schema(description = "视频分辨率数组")
    @NotEmpty(message = "视频分辨率不能为空数组")
    private List<@NotNull VideoResolutionEnum> videoResolutionList;
}

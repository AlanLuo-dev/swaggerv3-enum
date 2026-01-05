package com.yx.swaggerv3_enum.response;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.enums.VideoResolutionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "包含 枚举数组的JSON对象")
public class ColorBatchVO {

    @Schema(description = "颜色列表")
    private List<ColorEnum> colors;

    @Schema(description = "视频分辨率列表")
    private List<VideoResolutionEnum> videoResolutions;
}

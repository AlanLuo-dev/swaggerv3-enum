package com.yx.swaggerv3_enum.request;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.enums.ContactPhoneEnum;
import com.yx.swaggerv3_enum.enums.TaxRateEnum;
import com.yx.swaggerv3_enum.enums.VideoResolutionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "包含 枚举数组的JSON对象")
public class ColorBatchDTO {

    @Schema(description = "（入参）税率列表")
    private List<TaxRateEnum> taxRates;

    @Schema(description = "（入参）颜色列表")
    private List<ColorEnum> colors;

    @Schema(description = "（入参）视频分辨率列表")
    private List<VideoResolutionEnum> videoResolutions;

    @Schema(description = "（入参）联系人手机号码列表（Long类型）")
    private List<ContactPhoneEnum> contactPhones;
}

package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.request.ColorFormBatchDTO;
import com.yx.swaggerv3_enum.request.ColorFormDTO;
import com.yx.swaggerv3_enum.response.ColorFormBatchVO;
import com.yx.swaggerv3_enum.response.ColorFormVO;
import com.yx.swaggerv3_enum.response.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "表单入参")
@RestController
@RequestMapping(value = "/api")
public class FormController {

    @Operation(summary = "application/x-www-form-urlencoded 表单（枚举）")
    @PostMapping(
            value = "/application/x-www-form-urlencoded/enum",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResultVO<ColorFormVO> application_x_www_form_urlencoded_enum(@Validated @Parameter ColorFormDTO form) {
        return new ResultVO<>(new ColorFormVO(form.getColor(), form.getNote()));
    }

    @Operation(summary = "application/x-www-form-urlencoded 表单（枚举数组）")
    @PostMapping(
            value = "/application/x-www-form-urlencoded/enum-array",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResultVO<ColorFormBatchVO> application_x_www_form_urlencoded_enum_array(@Validated @Parameter ColorFormBatchDTO form) {
        ColorFormBatchVO colorFormBatchVO = new ColorFormBatchVO();
        colorFormBatchVO.setColorList(form.getColorList());
        colorFormBatchVO.setVideoResolutionList(form.getVideoResolutionList());
        return new ResultVO<>(colorFormBatchVO);
    }

    @Operation(summary = "multipart/form-data 表单（枚举）")
    @PostMapping(
            value = "/multipart/form-data/enum",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResultVO<ColorFormVO> multipart_form_data_enum(@Validated @Parameter ColorFormDTO form) {
        return new ResultVO<>(new ColorFormVO(form.getColor(), form.getNote()));
    }
}

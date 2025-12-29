package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.request.ColorBatchDTO;
import com.yx.swaggerv3_enum.request.ColorDTO;
import com.yx.swaggerv3_enum.request.ColorForm;
import com.yx.swaggerv3_enum.request.ColorQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "测试枚举作为入参的各种情况")
@RestController
@RequestMapping(value = "/api")
public class EnumTestController {

    @Operation(summary = "根据请求参数获取颜色")
    @GetMapping("/color/param")
    public ColorEnum getByParam(@Parameter(description = "颜色值", schema = @Schema(implementation = ColorEnum.class))
                                @RequestParam ColorEnum color) {
        return color;
    }

    @Operation(summary = "根据路径变量获取颜色")
    @GetMapping("/color/{color}")
    public ColorEnum getByPath(@Parameter(description = "颜色值", schema = @Schema(implementation = ColorEnum.class))
                               @PathVariable ColorEnum color) {
        return color;
    }

    @Operation(summary = "颜色查询（对象参数）")
    @GetMapping("/color/query")
    public ColorQuery query(ColorQuery query) {
        return query;
    }

    @Operation(summary = "POST方法提交JSON（枚举 作为Controller方法的参数）")
    @PostMapping("/RequestBody/Enum_As_Controller_Method_Parameter")
    public ColorEnum _RequestBody_Enum_As_Controller_Method_Parameter(@RequestBody(description = "颜色对象")
                                                                      @org.springframework.web.bind.annotation.RequestBody ColorEnum color) {
        return color;
    }

    @Operation(summary = "POST方法提交JSON（枚举 作为对象参数的属性）")
    @PostMapping("/RequestBody/Enum_As_Property_In_Object")
    public ColorDTO _RequestBody_Enum_As_Property_In_Object(
            @org.springframework.web.bind.annotation.RequestBody ColorDTO dto) {
        return dto;
    }

    @Operation(summary = "POST方法提交JSON（枚举数组 作为Controller方法的参数）")
    @PostMapping("/RequestBody/EnumArray_As_Controller_Method_Parameter")
    public List<ColorEnum> _RequestBody_EnumArray_As_Controller_Method_Parameter(
            @RequestBody(
                    description = "颜色枚举列表",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = ColorEnum.class,
                                    type = "array"
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody List<ColorEnum> colors) {
        return colors;
    }

    @Operation(summary = "POST方法提交JSON (枚举数组 作为对象参数的属性)")
    @PostMapping("/RequestBody/EnumArray_As_Parameter_In_Object")
    public ColorBatchDTO _RequestBody_EnumArray_As_Parameter_In_Object(
            @RequestBody(
                    description = "颜色批量对象",
                    required = true
            )
            @org.springframework.web.bind.annotation.RequestBody ColorBatchDTO dto) {
        return dto;
    }

    @Operation(summary = "表单方式提交颜色")
    @PostMapping("/color/form")
    public ColorEnum postForm(
            @Parameter(
                    description = "颜色",
                    schema = @Schema(implementation = ColorEnum.class)
            )
            @RequestParam ColorEnum color) {
        return color;
    }

    @Operation(summary = "提交表单：application/x-www-form-urlencoded （枚举 作为对象参数的属性）")
    @PostMapping(
            value = "/application/x-www-form-urlencoded/Enum_As_Property_In_Object",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ColorForm application_x_www_form_urlencoded_Enum_As_Property_In_Object(@Parameter ColorForm form) {
        return form;
    }

    @Operation(summary = "提交表单：multipart/form-data （枚举 作为对象参数的属性）")
    @PostMapping(
            value = "/multipart/form-data/Enum_As_Property_In_Object",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ColorForm multipart_form_data_Enum_As_Property_In_Object(@Parameter ColorForm form) {
        return form;
    }
}

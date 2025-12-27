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

    @Operation(summary = "提交颜色（枚举作为请求体）")
    @PostMapping("/color")
    public ColorEnum postColor(@RequestBody(description = "颜色枚举值", required = true, content = @Content(schema = @Schema(implementation = ColorEnum.class)))
                               @org.springframework.web.bind.annotation.RequestBody ColorEnum color) {
        return color;
    }

    @Operation(summary = "提交颜色对象")
    @PostMapping("/color/object")
    public ColorDTO postColorObject(
            @RequestBody(
                    description = "颜色对象",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ColorDTO.class)
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody ColorDTO dto) {
        return dto;
    }

    @Operation(summary = "批量提交颜色")
    @PostMapping("/color/batch")
    public List<ColorEnum> postColorBatch(
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

    @Operation(summary = "提交包含 枚举数组的JSON对象")
    @PostMapping("/color/batch/object")
    public ColorBatchDTO postBatchObject(
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

    @Operation(summary = "表单对象方式提交颜色")
    @PostMapping("/color/form/object")
    public ColorForm postFormObject(ColorForm form) {
        return form;
    }

}

package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.request.ColorBatchQuery;
import com.yx.swaggerv3_enum.request.ColorQuery;
import com.yx.swaggerv3_enum.response.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "GET URL 参数")
@RestController
@RequestMapping(value = "/api")
public class GetController {

    /* START -->  Query参数 ----------------------------------*/
    @Operation(summary = "Query参数（枚举）")
    @GetMapping("/QueryString/enum-in-method")
    public ResultVO<ColorEnum> _QueryString(@Parameter(description = "颜色值") @RequestParam ColorEnum color) {
        return new ResultVO<>(color);
    }

    @Operation(summary = "Query参数（枚举在对象中）")
    @GetMapping("/QueryString/enum-in-object")
    public ResultVO<ColorEnum> _QueryStringEnumInObject(@ParameterObject ColorQuery query) {
        return new ResultVO<>(query.getColor());
    }

    @Operation(summary = "Query参数（枚举数组）")
    @GetMapping("/QueryString/enum-array-in-method")
    public ResultVO<List<ColorEnum>> _QueryStringEnumArray(@Parameter(description = "颜色数组")
                                                           @RequestParam List<ColorEnum> colorList) {
        return new ResultVO<>(colorList);
    }

    @Operation(summary = "Query参数（枚举数组在对象中）")
    @GetMapping("/QueryString/enum-array-in-object")
    public ResultVO<List<ColorEnum>> _QueryStringEnumArrayInObject(@ParameterObject ColorBatchQuery query) {
        return new ResultVO<>(query.getColorList());
    }
    /* END -->  Query参数 ----------------------------------*/


    /* START -->  Path参数 ----------------------------------*/
    @Operation(summary = "Path参数（枚举）")
    @GetMapping("/Path/enum-in-method/{color}")
    public ResultVO<ColorEnum> _Path(@Parameter(description = "颜色值") @PathVariable ColorEnum color) {
        return new ResultVO<>(color);
    }

    @Operation(summary = "Path参数（枚举数组）")
    @GetMapping("/Path/enum-array-in-method/{colorList}")
    public ResultVO<List<ColorEnum>> _PathEnumArray(@Parameter(description = "颜色数组")
                                                    @PathVariable List<ColorEnum> colorList) {
        return new ResultVO<>(colorList);
    }
    /* END -->  Path参数 ----------------------------------*/


}

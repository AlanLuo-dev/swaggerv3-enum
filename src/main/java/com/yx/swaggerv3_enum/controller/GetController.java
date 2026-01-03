package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.enums.ColorEnum;
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

@Tag(name = "GET URL 参数")
@RestController
@RequestMapping(value = "/api")
public class GetController {

//    @Operation(summary = "GET方法 枚举作为Controller方法的 Query参数")
//    @GetMapping("/QueryString")
//    public ResultVO<ColorEnum> _QueryString(@Parameter(description = "颜色值") @RequestParam ColorEnum color) {
//        return new ResultVO<>(color);
//    }
//
//    @Operation(summary = "GET方法 枚举作为 Controller方法的 Path参数")
//    @GetMapping("/Path/{color}")
//    public ResultVO<ColorEnum> _Path(@Parameter(description = "颜色值") @PathVariable ColorEnum color) {
//        return new ResultVO<>(color);
//    }
//
//    @Operation(summary = "GET方法 枚举作为对象属性形式的 Query参数")
//    @GetMapping("/QueryStringInObject")
//    public ResultVO<ColorEnum> _QueryStringInObject(@ParameterObject ColorQuery query) {
//        return new ResultVO<>(query.getColor());
//    }
}

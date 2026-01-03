package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.request.ColorBatchDTO;
import com.yx.swaggerv3_enum.request.ColorDTO;
import com.yx.swaggerv3_enum.request.ColorForm;
import com.yx.swaggerv3_enum.request.ColorQuery;
import com.yx.swaggerv3_enum.response.ColorVO;
import com.yx.swaggerv3_enum.response.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "测试枚举作为入参的各种情况")
@RestController
@RequestMapping(value = "/api")
public class EnumTestController {

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
//
//    @Operation(summary = "POST方法提交JSON（枚举 作为Controller方法的参数）")
//    @PostMapping("/RequestBody/Enum_As_Controller_Method_Parameter")
//    public ResultVO<ColorEnum> _RequestBody_Enum_As_Controller_Method_Parameter(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "颜色对象")
//            @RequestBody ColorEnum color) {
//        return new ResultVO<>(color);
//    }
//
//
//
//    @Operation(summary = "POST方法提交JSON（枚举数组 作为Controller方法的参数）")
//    @PostMapping("/RequestBody/EnumArray_As_Controller_Method_Parameter")
//    public ResultVO<List<ColorEnum>> _RequestBody_EnumArray_As_Controller_Method_Parameter(
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "颜色枚举列表",
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(
//                                    implementation = ColorEnum.class,
//                                    type = "array"
//                            )
//                    )
//            )
//            @RequestBody List<ColorEnum> colors) {
//        return new ResultVO<>(colors);
//    }

    @Operation(summary = "POST方法提交JSON（枚举 作为对象参数的属性）")
    @PostMapping("/RequestBody/Enum_As_Property_In_Object")
    public ResultVO<ColorVO> _RequestBody_Enum_As_Property_In_Object(@RequestBody ColorDTO dto) {
        return new ResultVO<>(new ColorVO(dto.getColor(), dto.getUsername()));
    }

    @Operation(summary = "POST方法提交JSON (枚举数组 作为对象参数的属性)")
    @PostMapping("/RequestBody/EnumArray_As_Parameter_In_Object")
    public ResultVO<List<ColorEnum>> _RequestBody_EnumArray_As_Parameter_In_Object(@RequestBody ColorBatchDTO dto) {
        return new ResultVO<>(dto.getColors());
    }

//    @Operation(summary = "表单方式提交颜色")
//    @PostMapping("/color/form")
//    public ResultVO<ColorEnum> postForm(@Parameter(description = "颜色", schema = @Schema(implementation = ColorEnum.class))
//                                        @RequestParam ColorEnum color) {
//        return new ResultVO<>(color);
//    }
//
//    @Operation(summary = "提交表单：application/x-www-form-urlencoded （枚举 作为对象参数的属性）")
//    @PostMapping(
//            value = "/application/x-www-form-urlencoded/Enum_As_Property_In_Object",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResultVO<ColorForm> application_x_www_form_urlencoded_Enum_As_Property_In_Object(@Parameter ColorForm form) {
//        return new ResultVO<>(form);
//    }
//
//    @Operation(summary = "提交表单：multipart/form-data （枚举 作为对象参数的属性）")
//    @PostMapping(
//            value = "/multipart/form-data/Enum_As_Property_In_Object",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResultVO<ColorForm> multipart_form_data_Enum_As_Property_In_Object(@Parameter ColorForm form) {
//        return new ResultVO<>(form);
//    }
}

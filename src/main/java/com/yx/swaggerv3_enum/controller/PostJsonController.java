package com.yx.swaggerv3_enum.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.request.ColorBatchDTO;
import com.yx.swaggerv3_enum.request.ColorDTO;
import com.yx.swaggerv3_enum.response.ColorBatchVO;
import com.yx.swaggerv3_enum.response.ColorVO;
import com.yx.swaggerv3_enum.response.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Post Json入参")
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class PostJsonController {

    private final ObjectMapper objectMapper;


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
    public ResultVO<ColorBatchVO> _RequestBody_EnumArray_As_Parameter_In_Object(@RequestBody ColorBatchDTO dto) {
        objectMapper.getRegisteredModuleIds()
                .forEach(System.out::println);

        System.out.println("WRITE_ENUMS_USING_TO_STRING: " + objectMapper.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING));
        System.out.println("USE_BIG_DECIMAL_FOR_FLOATS: " + objectMapper.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS));
        System.out.println("FAIL_ON_UNKNOWN_PROPERTIES: " + objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));



        try {

            LocalDateTime now = LocalDateTime.of(2026, 1, 11, 15, 30, 45);
            String json = objectMapper.writeValueAsString(now);
            System.out.println(json);  // 应该是 "2026-01-11 15:30:45"

            String str = "\"2026-01-11 15:30:45\"";
            LocalDateTime dt = objectMapper.readValue(str, LocalDateTime.class);
            System.out.println(dt); // 应该解析成 LocalDateTime

            String value = "\"red\"";
            ColorEnum color = objectMapper.readValue(value, ColorEnum.class);
            System.out.println(color); // RED
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ResultVO<>(new ColorBatchVO(dto.getTaxRates(), dto.getColors(), dto.getVideoResolutions(), dto.getContactPhones()));
    }

    //    @Operation(summary = "表单方式提交颜色")
//    @PostMapping("/color/form")
//    public ResultVO<ColorEnum> postForm(@Parameter(description = "颜色", schema = @Schema(implementation = ColorEnum.class))
//                                        @RequestParam ColorEnum color) {
//        return new ResultVO<>(color);
//    }
//

}

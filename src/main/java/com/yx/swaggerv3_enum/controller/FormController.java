package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.enums.ColorEnum;
import com.yx.swaggerv3_enum.request.ColorFormBatchDTO;
import com.yx.swaggerv3_enum.request.ColorFormDTO;
import com.yx.swaggerv3_enum.response.ColorFormVO;
import com.yx.swaggerv3_enum.response.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "表单入参")
@RestController
@RequestMapping(value = "/api")
public class FormController {

//    @Operation(summary = "提交表单：application/x-www-form-urlencoded （枚举 作为对象参数的属性）")
//    @PostMapping(
//            value = "/application/x-www-form-urlencoded/Enum_As_Property_In_Object",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResultVO<ColorFormVO> application_x_www_form_urlencoded_Enum_As_Property_In_Object(@Parameter ColorFormDTO form) {
//        return new ResultVO<>(new ColorFormVO(form.getColor(), form.getNote()));
//    }

    @Operation(summary = "提交表单：application/x-www-form-urlencoded （枚举数组 作为对象参数的属性）")
    @PostMapping(
            value = "/application/x-www-form-urlencoded/EnumArray_As_Property_In_Object",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResultVO<List<ColorEnum>> application_x_www_form_urlencoded_EnumArray_As_Property_In_Object(@Parameter ColorFormBatchDTO form) {
        return new ResultVO<>(form.getColorList());
    }

//    @Operation(summary = "提交表单：multipart/form-data （枚举 作为对象参数的属性）")
//    @PostMapping(
//            value = "/multipart/form-data/Enum_As_Property_In_Object",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResultVO<ColorFormVO> multipart_form_data_Enum_As_Property_In_Object(@Parameter ColorFormDTO form) {
//        return new ResultVO<>(new ColorFormVO(form.getColor(), form.getNote()));
//    }
}

package com.yx.swaggerv3_enum.controller;

import com.yx.swaggerv3_enum.request.EnumJsonQuery;
import com.yx.swaggerv3_enum.request.FormDTO;
import com.yx.swaggerv3_enum.request.UrlQuery;
import com.yx.swaggerv3_enum.response.EnumJsonVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EnumController")
public class TestController {

    @Operation(summary = "测试@RequestBody接收 枚举参数")
    @PostMapping(value = "/testJsonEnum")
    public EnumJsonVO testJsonEnum(@RequestBody EnumJsonQuery jsonQuery) {
        EnumJsonVO enumJsonVO = new EnumJsonVO();
        enumJsonVO.setColor(jsonQuery.getColorEnum());
        enumJsonVO.setUsername(jsonQuery.getUsername());
        return enumJsonVO;
    }

    @Operation(summary = "测试普通表单")
    @PostMapping(value = "/testFormDTO", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormDTO testFormDTO(@Parameter FormDTO formDTO) {
        return formDTO;
    }


    @Operation(summary = "测试URL传参枚举接收参数")
    @GetMapping(value = "/testEnumUrlParams")
    public UrlQuery testJsonEnum(@ParameterObject UrlQuery urlQuery) {
        return urlQuery;
    }
}

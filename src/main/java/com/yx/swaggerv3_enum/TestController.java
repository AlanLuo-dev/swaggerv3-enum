package com.yx.swaggerv3_enum;

import com.yx.swaggerv3_enum.enums.FormDTO;
import com.yx.swaggerv3_enum.enums.UrlQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EnumController")
public class TestController {


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

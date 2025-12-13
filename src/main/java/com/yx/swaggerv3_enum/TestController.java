package com.yx.swaggerv3_enum;

import com.yx.swaggerv3_enum.enums.EnumJsonQuery;
import com.yx.swaggerv3_enum.enums.FormDTO;
import com.yx.swaggerv3_enum.enums.TestCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/EnumController")
public class TestController {

    @Operation(summary = "测试枚举接收参数")
    @GetMapping(value = "/testEnum")
    public TestCodeEnum test(@Parameter(description = "枚举参数", required = true) @RequestParam TestCodeEnum testCodeEnum) {
        return testCodeEnum;
    }

    @Operation(summary = "测试Json枚举接收参数")
    @PostMapping(value = "/testEnum")
    public EnumJsonQuery testJsonEnum(@RequestBody EnumJsonQuery jsonQuery) {
        return jsonQuery;
    }

    @Operation(summary = "测试普通表单")
    @PostMapping(value = "/testFormDTO", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FormDTO testFormDTO(@Parameter FormDTO formDTO) {
        return formDTO;
    }

}

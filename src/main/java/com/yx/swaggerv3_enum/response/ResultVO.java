package com.yx.swaggerv3_enum.response;

import com.yx.swaggerv3_enum.enums.ColorEnum; 
import io.swagger.v3.oas.annotations.media.Schema; 
import lombok.Data;

@Data 
public class ResultVO { 
    
    @Schema(description = "返回的颜色") 
    private ColorEnum color; 
}

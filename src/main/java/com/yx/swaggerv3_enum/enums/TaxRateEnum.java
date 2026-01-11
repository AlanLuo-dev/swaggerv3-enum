package com.yx.swaggerv3_enum.enums;

import com.yx.swaggerv3_enum.config.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum TaxRateEnum implements EnumDef<BigDecimal, TaxRateEnum> {

    ZERO(new BigDecimal("0.00"), "免税"),
    LOW(new BigDecimal("0.06"), "6%税率"),
    NORMAL(new BigDecimal("0.13"), "13%税率");

    private final BigDecimal value;
    private final String label;
}


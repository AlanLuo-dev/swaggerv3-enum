package com.yx.swaggerv3_enum.enums;

import com.yx.swaggerv3_enum.config.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 联系人枚举
 * <p>
 * 现实场景：常用联系人手机号码枚举
 * 场景说明：11位手机号码，日常高频使用，采用long类型存储更符合开发惯例
 */
@Getter
@RequiredArgsConstructor
public enum ContactPhoneEnum implements EnumDef<Long, ContactPhoneEnum> {


    EMERGENCY_CONTACT(13800138000L, "紧急联系人（张三）"),
    HOME_PHONE(13900139000L, "家庭电话（母亲）"),
    WORK_COLLEAGUE(13700137000L, "工作同事（李四）"),
    FRIEND(13600136000L, "好友（王五）");

    /**
     * 声明long类型成员变量：存储11位手机号码
     */
    private final Long value;

    /**
     * 附加现实属性：联系人备注（贴合日常使用场景）
     */
    private final String label;


}

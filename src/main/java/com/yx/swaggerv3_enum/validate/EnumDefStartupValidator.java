package com.yx.swaggerv3_enum.validate;

import com.yx.swaggerv3_enum.config.core.EnumDef;
import org.reflections.Reflections;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class EnumDefStartupValidator implements SmartInitializingSingleton {

    private final ApplicationContext applicationContext;

    public EnumDefStartupValidator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        List<String> basePackages = AutoConfigurationPackages.get(applicationContext);
        scanAndValidateEnumDef(basePackages);
    }

    private void scanAndValidateEnumDef(List<String> basePackages) {
        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<? extends Enum>> enums = reflections.getSubTypesOf(Enum.class);
            for (Class<? extends Enum> enumClass : enums) {
                if (EnumDef.class.isAssignableFrom(enumClass)) {
                    EnumDefValidator.validate(enumClass);
                }
            }
        }
    }
}



package com.yx.swaggerv3_enum.config.springdoc;

import com.fasterxml.jackson.databind.JavaType;
import com.yx.swaggerv3_enum.config.core.EnumDef;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverterContextImpl;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumDefPropertyCustomizer implements PropertyCustomizer {

    @Override
    @SuppressWarnings("rawtypes")
    public Schema customize(Schema schema, AnnotatedType annotatedType) {
        System.out.println("————————————————————————————————————————————————————————————————————————————");
        System.out.println("schame: " + schema.getDescription() + "  schema Type: " + schema.getType());
        if (annotatedType.getType() instanceof JavaType type && type.isEnumType() && isCodeEnum(type.getRawClass())) {
            List<EnumDef<? extends Serializable, ?>> enumConstants =
                    List.of((EnumDef<? extends Serializable, ?>[])type.getRawClass().getEnumConstants());

            String description = enumConstants.stream()
                    .map(enumSchema -> enumSchema.getValue() + " = " + enumSchema.getLabel())
                    .collect(Collectors.joining("，", "<b>（", "）</b>"));
            String existDescription = schema.getDescription();
            Optional<? extends Serializable> optional = enumConstants.stream().map(EnumDef::getValue).findFirst();
            if (optional.isPresent()) {
                Serializable serializable = optional.get();
                schema = PrimitiveType.createProperty(serializable.getClass());
                schema.setExample(serializable);
            }

            schema.setEnum(enumConstants.stream().map(EnumDef::getValue).toList());
            schema.setDescription(existDescription + description);


            Function<AnnotatedType, Schema> jsonUnwrappedHandler = annotatedType.getJsonUnwrappedHandler();
            if (Objects.isNull(jsonUnwrappedHandler)) {
                return schema;
            }
            try {
                ModelConverterContextImpl modelConverterContext = getArg3FromLambda(jsonUnwrappedHandler);
                HashSet<AnnotatedType> processedTypesFromContext = getProcessedTypesFromContext(modelConverterContext);
                for (AnnotatedType _annotatedType : processedTypesFromContext) {
                    for (Annotation ctxAnnotation : _annotatedType.getCtxAnnotations()) {
                        if (ctxAnnotation instanceof RequestBody
                                || ctxAnnotation instanceof Parameter
                                || ctxAnnotation instanceof Validated) {
                            System.out.println(" >>> 找到 @" + ctxAnnotation.annotationType().getSimpleName() + " 注解，当前参数是请求参数!!");

                            return schema;
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            EnumDef<? extends Serializable, ?> enumConstant = enumConstants.stream().findFirst().orElse(null);
            if (Objects.nonNull(enumConstant)) {
                schema = createObjectSchema(enumConstant);

                return schema;
            }

        }

        // ~ START  ======表单场景：处理数组类型字段，合并items的枚举描述到顶层 ===============
        if (schema instanceof ArraySchema) {
            Schema itemsSchema = schema.getItems();
            // 检查 items 是否是枚举类型且包含拼接的枚举描述
            if (itemsSchema != null && itemsSchema.getDescription() != null
                    && itemsSchema.getDescription().contains("<b>（")
                    && schema.getDescription() != null ) {
                // 将items的枚举描述合并到数组字段的顶层description
                String enumDesc = itemsSchema.getDescription().substring(
                        itemsSchema.getDescription().indexOf("<b>（"));
                schema.setDescription(schema.getDescription() + enumDesc);
            }
        }
        // ~ END    ======表单场景：处理数组类型字段，合并items的枚举描述到顶层 ===============

        // ~ START ========= 为返回参数的枚举的 Schema 实现对象化（即转为 ObjectSchema） ========================================
        Function<AnnotatedType, Schema> jsonUnwrappedHandler = annotatedType.getJsonUnwrappedHandler();
        if (Objects.isNull(jsonUnwrappedHandler)) {
            return schema;
        }
        try {
            ModelConverterContextImpl modelConverterContext = getArg3FromLambda(jsonUnwrappedHandler);
            HashSet<AnnotatedType> processedTypesFromContext = getProcessedTypesFromContext(modelConverterContext);
            Map<Schema, List<EnumDef<? extends Serializable, ?>>> schemaEnumMap = new HashMap<>();

            // 收集 枚举类型的字段的 schema 和 枚举的对应关系，组成HashMap
            for  (AnnotatedType _annotatedType : processedTypesFromContext) {
                if (_annotatedType.getType() instanceof JavaType type && type.isEnumType() && isCodeEnum(type.getRawClass())) {
                    Schema resolve = modelConverterContext.resolve(_annotatedType);

                    List<EnumDef<? extends Serializable, ?>> enumConstants =
                            List.of((EnumDef<? extends Serializable, ?>[]) type.getRawClass().getEnumConstants());
                    schemaEnumMap.put(resolve, enumConstants);
                }
            }

            // 返回参数标识：true = 当前Schema是返回参数，false = 当前Schema是请求参数
            boolean isResponseParam = true;
            outerLoop:// 定义外层循环标签
            for (AnnotatedType _annotatedType : processedTypesFromContext) {
                System.out.println("       processedTypes: " + _annotatedType.getType());
                for (Annotation ctxAnnotation : _annotatedType.getCtxAnnotations()) {
                    if (ctxAnnotation instanceof RequestBody
                            || ctxAnnotation instanceof Parameter
                            || ctxAnnotation instanceof Validated) {
                        System.out.println("       找到 @" + ctxAnnotation.annotationType().getSimpleName() + " 注解，当前参数" + schema.getName() + " 是请求参数!!");
                        isResponseParam = false;
                        break outerLoop; // 跳出外层循环
                    }
                }
            }

            // ----------------------------------------------------------------
            System.out.println("是否为返回参数：" +  isResponseParam);
            // 为返回参数的 example 执行对象化
            if (isResponseParam) {
                Schema items = schema.getItems();
                if (schemaEnumMap.containsKey(items)) {
                    List<EnumDef<? extends Serializable, ?>> enumConstants = schemaEnumMap.get(items);
                    Optional<EnumDef<? extends Serializable, ?>> optional = enumConstants.stream().findFirst();
                    if (optional.isPresent()) {
                        items = createObjectSchema(optional.get());
                        schema.items(items);
                    }
                }
                System.out.println("items: " + System.identityHashCode(items));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // ~ END ========= 为返回参数的枚举的 Schema 实现对象化（即转为 ObjectSchema） ========================================

        return schema;
    }

    /**
     * 创建 对象化的 Schema
     *
     * @param enumConstant 枚举
     * @return ObjectSchema 对象
     */
    private Schema createObjectSchema(EnumDef<? extends Serializable, ?> enumConstant) {
        Schema items = new ObjectSchema();

        Schema valueSchema = PrimitiveType.createProperty(enumConstant.getValue().getClass());
        valueSchema.setExample(enumConstant.getValue());
        items.addProperty("value", valueSchema);

        Schema labelSchema = new StringSchema();
        labelSchema.setExample(enumConstant.getLabel());
        items.addProperty("label", labelSchema);

        // items.setRequired(schema.getRequired());
        // items.setNullable(schema.getNullable());
        // items.setDescription(schema.getDescription());

        return items;
    }

    private boolean isCodeEnum(Class<?> rawClass) {
        if (rawClass == null || !rawClass.isEnum()) {
            return false;
        }
        // 直接判断 rawClass 是否实现了 EnumSchema 接口(更直接、更可靠)
        return EnumDef.class.isAssignableFrom(rawClass);
    }

    /**
     * 从 Lambda 表达式（jsonUnwrappedHandler）中反射获取 arg$3 字段（ModelConverterContextImpl 实例）
     */
    private ModelConverterContextImpl getArg3FromLambda(Function<AnnotatedType, Schema> lambdaInstance) throws Exception {

        // 获取 Lambda 实例的实际类型（匿名内部类）
        Class<?> lambdaClass = lambdaInstance.getClass();
        Field arg3Field = null;

        // 遍历所有声明字段（包括合成字段、私有字段，必须用 getDeclaredFields()，不能用 getFields()）
        for (Field field : lambdaClass.getDeclaredFields()) {
            // 匹配字段名 arg$3（注意：字段名是固定的，与你调试观察一致）
            if ("arg$3".equals(field.getName())) {
                arg3Field = field;
                break;
            }
        }

        if (arg3Field == null) {
            System.out.println("未找到 arg$3 字段");
            return null;
        }

        // 暴力突破访问权限检查（关键：私有字段必须设置 setAccessible(true)）
        arg3Field.setAccessible(true);

        // 获取字段值并强转为 ModelConverterContextImpl
        Object fieldValue = arg3Field.get(lambdaInstance);
        if (!(fieldValue instanceof ModelConverterContextImpl)) {
            System.out.println("arg$3 字段类型不是 ModelConverterContextImpl");
            return null;
        }

        return (ModelConverterContextImpl) fieldValue;
    }

    /**
     * 从 ModelConverterContextImpl 中反射获取 processedTypes 字段
     */
    private HashSet<AnnotatedType> getProcessedTypesFromContext(ModelConverterContextImpl context) throws Exception {
        Field processedTypesField = ModelConverterContextImpl.class.getDeclaredField("processedTypes");
        // 同样需要突破访问权限
        processedTypesField.setAccessible(true);
        Object fieldValue = processedTypesField.get(context);

        if (!(fieldValue instanceof HashSet)) {
            System.out.println("processedTypes 字段类型不是 HashSet");
            return null;
        }

        return (HashSet<AnnotatedType>) fieldValue;
    }

}

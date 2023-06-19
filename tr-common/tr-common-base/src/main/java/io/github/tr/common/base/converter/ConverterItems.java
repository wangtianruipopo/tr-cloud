package io.github.tr.common.base.converter;

import io.github.tr.common.base.enums.BaseEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
public @interface ConverterItems {
    /**
     * 写死的码值关系
     * @return 码值关系
     */
    String[] items() default {};

    /**
     * 根据码值关系公共表从查询
     * categoryCode为所属大类编码
     * @return 码值关系
     */
    String categoryCode() default "";

    Class<? extends BaseEnum> enumList() default BaseEnum.class;

}

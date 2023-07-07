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
     * <h2>写死的码值关系</h2>
     * @return 码值关系
     */
    String[] items() default {};

    /**
     * <h2>根据码值关系公共表从查询</h2>
     * categoryCode为所属大类编码
     * @return 码值关系
     */
    String categoryCode() default "";

    /**
     * <h2>写死的码值关系，存储于枚举中</h2>
     * @return 码值关系
     */
    Class<? extends BaseEnum> enumList() default BaseEnum.class;

    /**
     * <h2>是否是list集合，需要全部拼接的值</h2>
     * @return 是是true，否是false
     */
    boolean multipart() default false;

}

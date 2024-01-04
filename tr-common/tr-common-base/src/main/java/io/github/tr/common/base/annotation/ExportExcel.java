package io.github.tr.common.base.annotation;

import io.github.tr.common.base.handler.ExcelNameHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface ExportExcel {
    /**
     * excel导出文件名
     * @return 文件名
     */
    String value() default "导出数据";

    String sheetName() default "Sheet";

    /**
     * excel导出对应类型
     * @return 导出对应类型
     */
    Class<?> type() default Void.class;

    /**
     * 根据查询参数决定Excel的文件名称及页签名称
     * @return 处理类类型
     */
    Class<? extends ExcelNameHandler> excelNameHandler() default ExcelNameHandler.class;
}

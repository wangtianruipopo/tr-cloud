package io.github.tr.common.base.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import io.github.tr.common.base.enums.BaseEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StringExcelConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Field field = contentProperty.getField();
        ConverterItems converterItems = field.getAnnotation(ConverterItems.class);
        if (converterItems == null) return null;
        String text = getText(value, false, converterItems.items(), converterItems.enumList(), converterItems.categoryCode());
        if (text != null) {
            return new WriteCellData<>(text);
        }
        return null;
    }

    public static String getText(String value, boolean multipart, String[] items, Class<? extends BaseEnum> enumList, String categoryCode) {
        Map<String, String> map = new HashMap<>();

        if (items != null && items.length > 0) {
            for (int i = 0; i < items.length; i += 2) {
                map.put(items[i], items[i + 1]);
            }
        }
        // 从枚举里取
        BaseEnum[] enums = enumList.getEnumConstants();
        if (enums != null) {
            for (BaseEnum baseEnum : enums) {
                map.put(baseEnum.getCode(), baseEnum.getName());
            }
        }
        return map.get(value);
    }
}

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
        String text = getText(value, field);
        if (text != null) {
            return new WriteCellData<>(text);
        }
        return null;
    }

    public static String getText(String value, Field field) {
        ConverterItems converterItems = field.getAnnotation(ConverterItems.class);
        if (converterItems == null) return null;
        String[] items = converterItems.items();
        if (items != null && items.length > 0) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < items.length; i += 2) {
                map.put(items[i], items[i + 1]);
            }
            return map.get(value);
        }
        // 从枚举里取
        Class<? extends BaseEnum> enumList = converterItems.enumList();

        BaseEnum[] enums = enumList.getEnumConstants();
        if (enums != null) {
            for (BaseEnum baseEnum : enums) {
                if (baseEnum.getCode().equals(value)) {
                    return baseEnum.getName();
                }
            }
        }
        return null;
    }
}

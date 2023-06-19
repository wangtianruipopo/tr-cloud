package io.github.tr.common.base.handler;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.List;

public class DynamicExcelHandler<T> implements SheetWriteHandler {

    private Class<T> clazz;
    private List<Field> fields;

    public DynamicExcelHandler(Class<T> clazz, List<Field> fields) {
        this.clazz = clazz;
        this.fields = fields;
    }

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        Sheet sheet = context.getWriteSheetHolder().getSheet();
        for (int i = 0; i < fields.size(); i++) {
            ColumnWidth columnWidth = fields.get(i).getAnnotation(ColumnWidth.class);
            if (columnWidth != null) {
                int value = columnWidth.value() * 250;
                sheet.setColumnWidth(i, value);
            }

        }
    }
}
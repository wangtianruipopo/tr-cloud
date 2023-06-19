package io.github.tr.common.base.handler;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.reflect.Field;
import java.util.List;

public class DynamicExcelHandler implements SheetWriteHandler {

    private final List<Field> fields;

    public DynamicExcelHandler(List<Field> fields) {
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
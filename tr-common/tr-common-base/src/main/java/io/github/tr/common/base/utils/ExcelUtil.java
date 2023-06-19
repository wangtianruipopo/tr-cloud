package io.github.tr.common.base.utils;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.github.tr.common.base.converter.StringExcelConverter;
import io.github.tr.common.base.handler.DynamicExcelHandler;
import io.github.tr.common.base.query.QueryFunction;
import io.github.tr.common.base.query.QueryParams;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel工具类
 *
 * @author wangtianrui
 */
@Data
@Builder
@Slf4j
public class ExcelUtil {
    private OutputStream outputStream;
    private QueryParams<Map<String, Object>> params;
    private Class<?> exportType;
    private QueryFunction queryFunction;
    private String sheetName;
    private Collection<WriteHandler> writeHandler;
    private Collection<Integer> columnsByIndex;
    private Collection<String> columnsByName;

    public void export() {
        ExcelWriter excelWriter = null;
        ExcelWriterBuilder excelwriterbuilder;
        try {
            final List<Field> fields = getCustomField();
            if (isCustomHead()) {
                assert fields != null;
                List<List<String>> head = getCustomHead(fields);
                excelwriterbuilder = EasyExcel.write(outputStream).head(head);
                excelwriterbuilder.registerWriteHandler(new DynamicExcelHandler(fields));
            } else {
                excelwriterbuilder = EasyExcel.write(outputStream, exportType);
            }
            if (writeHandler != null && !writeHandler.isEmpty()) {
                for (WriteHandler writeHandler : writeHandler) {
                    excelwriterbuilder.registerWriteHandler(writeHandler);
                }
            }
            excelWriter = excelwriterbuilder.build();
            // 如果pageIndex和pageSize任意一个包含-1，则表示导出全部页
            if (params.getPageIndex() == -1 || params.getPageSize() == -1) {
                params.setPageSize(100000);
                int index = 0;
                while (true) {
                    params.setPageIndex(params.getPageIndex() + index);
                    List<?> data = queryFunction.data(params);
                    if (data == null || data.isEmpty()) {
                        break;
                    }
                    String sheetName = this.sheetName + (index + 1);
                    WriteSheet writeSheet = EasyExcel.writerSheet(index++, sheetName).build();
                    writeSheet(data, fields, excelWriter, writeSheet);
                }
            } else {
                // 导出当前页
                List<?> data = queryFunction.data(params);
                WriteSheet writeSheet = EasyExcel.writerSheet(0, this.sheetName).build();
                writeSheet(data, fields, excelWriter, writeSheet);
            }
        } catch (Exception e) {
            log.error("导出EXCEL失败", e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    private void writeSheet(List<?> data, List<Field> fields, ExcelWriter excelWriter, WriteSheet writeSheet) {
        if (isCustomHead()) {
            List<List<Object>> dataList = new ArrayList<>();
            data.forEach(item -> {
                List<Object> itemList = new ArrayList<>();
                assert fields != null;
                fields.forEach(f -> {
                    try {
                        Object value = io.github.tr.common.base.utils.ReflectUtil.getValueByMethod(item, f);
                        if (value != null) {
                            // 获取converter属性
                            ExcelProperty excelProperty = f.getAnnotation(ExcelProperty.class);
                            Class<? extends Converter<?>> converter = excelProperty.converter();
                            if (converter != null && !converter.equals(AutoConverter.class)) {
                                String text = StringExcelConverter.getText((String) value, f);
                                itemList.add(text);
                            } else {
                                itemList.add(value);
                            }
                        } else {
                            itemList.add(null);
                        }

                    } catch (Exception e) {
                        log.error("导出异常！", e);
                    }
                });
                dataList.add(itemList);
            });
            excelWriter.write(dataList, writeSheet);
        } else {
            excelWriter.write(data, writeSheet);
        }
    }

    private boolean isIndex() {
        return columnsByIndex != null && !columnsByIndex.isEmpty();
    }

    private boolean isName() {
        return columnsByName != null && !columnsByName.isEmpty();
    }

    private boolean isCustomHead() {
        return isIndex() || isName();
    }

    private List<Field> getCustomField() {
        boolean isIndex = isIndex();
        boolean isName = isName();
        if (!isIndex && !isName) {
            return null;
        }
        // 先将实体类的所有excel字段取出
        Field[] fields = ReflectUtil.getFields(exportType, f -> f.getAnnotation(ExcelProperty.class) != null);
        List<Field> realFields = new ArrayList<>();
        for (Field f : fields) {
            boolean flag;
            if (isName) {
                String fieldName = f.getName();
                flag = columnsByName.contains(fieldName);
            } else {
                ExcelProperty excelProperty = f.getAnnotation(ExcelProperty.class);
                int index = excelProperty.index();
                flag = columnsByIndex.contains(index);
            }
            if (flag) {
                f.setAccessible(true);
                realFields.add(f);
            }
        }
        realFields.sort(Comparator.comparingInt(a -> a.getAnnotation(ExcelProperty.class).index()));
        return realFields;
    }

    private List<List<String>> getCustomHead(List<Field> realFields) {
        List<List<String>> res = new ArrayList<>();
        realFields.forEach(f -> {
            ExcelProperty excelProperty = f.getAnnotation(ExcelProperty.class);
            String[] value = excelProperty.value();
            List<String> list = new ArrayList<>();
            Collections.addAll(list, value);
            res.add(list);
        });
        return res;
    }

}

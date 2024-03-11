package io.github.tr.common.base.utils;

import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.github.tr.common.base.converter.ConverterItems;
import io.github.tr.common.base.converter.StringExcelConverter;
import io.github.tr.common.base.converter.TransCode;
import io.github.tr.common.base.handler.DynamicExcelHandler;
import io.github.tr.common.base.query.QueryFunction;
import io.github.tr.common.base.query.QueryParams;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <h1>Excel工具类</h1>
 *
 * @author wangtianrui
 */
@Data
@Builder
@Slf4j
public class ExcelUtil {
    /**
     * <h2>excel文件的输出流</h2>
     */
    private OutputStream outputStream;
    /**
     * <h2>查询导出数据的参数</h2>
     */
    private QueryParams<Map<String, Object>> params;
    /**
     * <h2>导出数据的映射类类型</h2>
     */
    private Class<?> exportType;
    /**
     * <h2>导出数据的查询方法</h2>
     */
    private QueryFunction queryFunction;
    /**
     * <h2>导出excel的sheet页签名称</h2>
     */
    private String sheetName;
    /**
     * <h2>自定义的excel处理类集合</h2>
     */
    private Collection<WriteHandler> writeHandler;
    /**
     * <h2>决定展示哪些列（下标）的集合</h2>
     */
    private Collection<Integer> columnsByIndex;
    /**
     * <h2>决定展示哪些列（属性名称）的集合</h2>
     */
    private Collection<String> columnsByName;

    private boolean allColumn;

    /**
     * <h2>导出excel</h2>
     */
    public void export() {
        ExcelWriter excelWriter = null;
        ExcelWriterBuilder excelwriterbuilder;
        try {
            final List<Field> fields = getCustomField();
            if (isCustomHead()) {
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
            if (params != null && (params.getPageIndex() == -1 || params.getPageSize() == -1)) {
                params.setPageSize(100000);
                params.setPageIndex(1);
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

    /**
     * <h2>写入excel</h2>
     *
     * @param data        写入数据
     * @param fields      字段名集合
     * @param excelWriter easy excel的写入excel对象
     * @param writeSheet  写入excel的sheet页对象
     */
    private void writeSheet(List<?> data, List<Field> fields, ExcelWriter excelWriter, WriteSheet writeSheet) {
        if (isCustomHead()) {
            List<List<Object>> dataList = new ArrayList<>();
            if (!data.isEmpty()) {
                BeanDesc beanDesc = BeanUtil.getBeanDesc(data.get(0).getClass());
                data.forEach(item -> {
                    List<Object> itemList = new ArrayList<>();
                    assert fields != null;
                    fields.forEach(f -> {
                        try {
                            Method getter = beanDesc.getGetter(f.getName());
                            Object value = getter.invoke(item);
                            if (value != null) {
                                // 获取converter属性
                                ExcelProperty excelProperty = f.getAnnotation(ExcelProperty.class);
                                Class<? extends Converter<?>> converter = excelProperty.converter();
                                ConverterItems converterItems = f.getAnnotation(ConverterItems.class);
                                if (converterItems != null) {
                                    // 判断是否需要获取bean
                                    Class<? extends TransCode> transCodeClass = converterItems.transCodeClass();
                                    if (!TransCode.class.equals(transCodeClass)) {
                                        // 获取bean
                                        TransCode transCode = ReflectUtil.newInstance(transCodeClass);
                                        String text = transCode.getText(value, converterItems.categoryCode(), converterItems.multipart());
                                        itemList.add(text);
                                    } else if (converter.equals(StringExcelConverter.class)) {

                                        String text = StringExcelConverter.getText((String) value, false, converterItems.items(), converterItems.enumList(), converterItems.categoryCode());
                                        itemList.add(text);
                                    }
                                } else {
                                    if (converter.equals(AutoConverter.class)) {
                                        itemList.add(value);
                                    }
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
            }
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
        return allColumn || isIndex() || isName();
    }

    private List<Field> getCustomField() {
        boolean isIndex = isIndex();
        boolean isName = isName();
        // 先将实体类的所有excel字段取出
        Field[] fields = ReflectUtil.getFields(exportType, f -> f.getAnnotation(ExcelProperty.class) != null);
        List<Field> realFields = new ArrayList<>();
        for (Field f : fields) {
            boolean flag = true;
            if (isName) {
                String fieldName = f.getName();
                flag = columnsByName.contains(fieldName);
            } else if (isIndex) {
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

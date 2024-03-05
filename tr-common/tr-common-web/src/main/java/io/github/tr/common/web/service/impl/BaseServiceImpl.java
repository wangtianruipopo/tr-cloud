package io.github.tr.common.web.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.tr.common.base.annotation.ExportExcel;
import io.github.tr.common.base.exception.CheckEntityException;
import io.github.tr.common.base.exception.CheckEntityResult;
import io.github.tr.common.base.handler.ExcelNameHandler;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.base.utils.DownloadUtil;
import io.github.tr.common.base.utils.ExcelUtil;
import io.github.tr.common.web.service.IBaseExcelHandler;
import io.github.tr.common.web.service.IBaseService;
import io.github.tr.common.web.utils.IQueryFunction;
import io.github.tr.common.web.utils.ModelUtil;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Autowired
    BeanUtilsBean beanUtilsBean;

    @Override
    @Transactional
    public void saveEntity(T entity) {
        Serializable key = ModelUtil.getKey(entity);
        CheckEntityResult checkRes = new CheckEntityResult();
        if (key != null) {
            this.beforeUpdate(entity, key, checkRes);
        } else {
            this.beforeInsert(entity, checkRes);
        }
        if (checkRes.isPassed()) {
            // 校验通过
            this.saveOrUpdate(entity);
            if (key != null) {
                this.afterUpdate(entity);
            } else {
                this.afterInsert(entity);
            }
        } else {
            // 抛出异常
            throw new CheckEntityException(checkRes);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Transactional
    public T saveEntity(Map<String, Object> entity) {
        // 获取实体类类型
        Class<T> entityType = (Class<T>) ClassUtil.getTypeArgument(this.getClass(), 1);
        Assert.notNull(entityType);
        T instance = entityType.getDeclaredConstructor().newInstance();
        Assert.notNull(instance);
        beanUtilsBean.populate(instance, entity);
        saveEntity(instance);
        return instance;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Transactional
    public List<T> saveBatchEntity(List<Map<String, Object>> entityList) {
        // 获取实体类类型
        Class<T> entityType = (Class<T>) ClassUtil.getTypeArgument(this.getClass(), 1);
        Assert.notNull(entityType);
        List<T> saveList = new ArrayList<>();
        List<T> afterUpdateList = new ArrayList<>();
        List<T> afterInsertList = new ArrayList<>();
        for (Map<String, Object> entity : entityList) {
            T instance = entityType.getDeclaredConstructor().newInstance();
            beanUtilsBean.populate(instance, entity);
            Serializable key = ModelUtil.getKey(entity);
            CheckEntityResult checkRes = new CheckEntityResult();
            if (key != null) {
                this.beforeUpdate(instance, key, checkRes);
            } else {
                this.beforeInsert(instance, checkRes);
            }
            if (checkRes.isPassed()) {
                // 校验通过
                saveList.add(instance);
                if (key != null) {
                    afterUpdateList.add(instance);
                } else {
                    afterInsertList.add(instance);
                }
            } else {
                // 抛出异常
                throw new CheckEntityException(checkRes);
            }
        }
        // 批量更新
        this.saveOrUpdateBatch(saveList);
        afterUpdateList.forEach(this::afterUpdate);
        afterInsertList.forEach(this::afterInsert);
        return saveList;
    }

    @Override
    public IPage<?> query(QueryParams<Map<String, Object>> queryParams) {
        return this.query(queryParams, this::queryMapper);
    }

    @Override
    public IPage<?> query(QueryParams<Map<String, Object>> queryParams, IQueryFunction<T> fn) {
        // 获取查询参数
        Map<String, Object> p = queryParams.getFilter();
        int pageIndex = queryParams.getPageIndex();
        int pageSize = queryParams.getPageSize();
        Page<T> page = new Page<>();
        page.setCurrent(pageIndex);
        page.setSize(pageSize);
        return fn.apply(page, p, queryParams.getOrder());
    }

    @Override
    public Object get(Serializable id) {
        T t = this.getById(id);
        return getOneCommon(t);
    }


    @Override
    public Object getByFilter(Map<String, Object> queryParams) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryParams.forEach(queryWrapper::eq);
        T t = this.getOne(queryWrapper);
        return getOneCommon(t);
    }

    @Override
    public void delete(Serializable id) {
        CheckEntityResult checkRes = new CheckEntityResult();
        this.beforeDelete(id, checkRes);
        // 进行删除前校验
        if (checkRes.isPassed()) {
            this.removeById(id);
            this.afterDelete(id);
        } else {
            throw new CheckEntityException(checkRes);
        }
    }

    @Override
    @Transactional
    public void deleteBatch(List<Serializable> ids) {
        List<Serializable> realIds = new ArrayList<>();
        ids.forEach(id -> {
            CheckEntityResult checkRes = new CheckEntityResult();
            this.beforeDelete(id, checkRes);
            if (checkRes.isPassed()) {
                if (id instanceof Integer) {
                    Long _id = Long.valueOf(id.toString());
                    realIds.add(_id);
                } else {
                    realIds.add(id);
                }
            }
        });
        if (!realIds.isEmpty()) {
            this.removeBatchByIds(realIds);
        }
        realIds.forEach(this::afterDelete);
    }

    @SneakyThrows
    @Override
    public void exportExcel(QueryParams<Map<String, Object>> queryParams, HttpServletResponse response) {
        ExportExcel exportExcel = this.getClass().getAnnotation(ExportExcel.class);
        Class<?> entityType = ClassUtil.getTypeArgument(this.getClass(), 1);
        String fileName = "导出数据";
        String sheetName = "Sheet";
        if (exportExcel != null) {
            Class<? extends ExcelNameHandler> excelNameHandlerClass = exportExcel.excelNameHandler();
            if (!excelNameHandlerClass.equals(ExcelNameHandler.class)) {
                ExcelNameHandler handler = ReflectUtil.newInstance(excelNameHandlerClass);
                fileName = handler.fileName(queryParams);
                sheetName = handler.sheetName(queryParams);
            } else {
                if (!exportExcel.type().equals(Void.class)) {
                    entityType = exportExcel.type();
                }
                fileName = exportExcel.value();
                sheetName = exportExcel.sheetName();
            }
        }
        Collection<WriteHandler> writeHandlerList = new ArrayList<>();
        HorizontalCellStyleStrategy styleStrategy = getStyleStrategy();
        writeHandlerList.add(styleStrategy);
        ExcelUtil.ExcelUtilBuilder builder = ExcelUtil.builder();
        builder.sheetName(sheetName).exportType(entityType).params(queryParams)
                .outputStream(response.getOutputStream()).queryFunction(p -> this.query(p).getRecords());
        if (this instanceof IBaseExcelHandler) {
            IBaseExcelHandler handler = (IBaseExcelHandler) this;
            Collection<Integer> columnsByIndex = handler.columnsByIndex(queryParams);
            Collection<String> columnsByName = handler.columnsByName(queryParams);
            Collection<WriteHandler> customHandlerList = handler.writeHandler(queryParams);
            if (customHandlerList != null) {
                writeHandlerList.addAll(customHandlerList);
            }
            if (columnsByIndex != null) {
                builder.columnsByIndex(columnsByIndex);
            }
            if (columnsByName != null) {
                builder.columnsByName(columnsByName);
            }
        }
        builder.writeHandler(writeHandlerList);
        ExcelUtil excelUtil = builder.build();
        DownloadUtil.downExcel(response, fileName, res -> excelUtil.export());
    }

    private Object getOneCommon(T t) {
        if (t == null) return null;
        // 进行扩展处理
        Object res = this.extensionOne(t);
        if (res == null) {
            return t;
        }
        return res;
    }

    protected HorizontalCellStyleStrategy getStyleStrategy() {
        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
    }
}

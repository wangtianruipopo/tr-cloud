package io.github.tr.common.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.tr.common.base.exception.CheckEntityResult;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.web.service.IBaseService;
import io.github.tr.common.web.utils.ModelUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
public abstract class BaseController<S extends IBaseService<T>, T> {
    @Autowired
    protected S baseService;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @PostMapping("/beforeSaveCheck")
    public CheckEntityResult beforeSaveCheck(@RequestBody T entity) {
        Serializable key = ModelUtil.getKey(entity);
        CheckEntityResult result = new CheckEntityResult();
        if (key != null) {
            this.baseService.beforeUpdate(entity, key, result);
        }
        this.baseService.beforeInsert(entity, result);
        return result;
    }

    @Operation(summary = "新增或修改")
    @PostMapping("/save")
    public T save(@RequestBody Map<String, Object> entity) {
        return this.baseService.saveEntity(entity);
    }

    @Operation(summary = "根据主键id删除")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        this.baseService.delete(id);
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("/deleteBatch")
    public void deleteBatch(@RequestBody List<Serializable> ids) {
        this.baseService.deleteBatch(ids);
    }

    @Operation(summary = "表格查询")
    @PostMapping("/query")
    public IPage<?> query(@RequestBody QueryParams<Map<String, Object>> queryParams) {
        return this.baseService.query(queryParams);
    }

    @Operation(summary = "根据主键获取View信息")
    @GetMapping("/get/{id}")
    public Object get(@PathVariable("id") Serializable id) {
        return this.baseService.get(id);
    }

    @Operation(summary = "根据条件获取View信息")
    @PostMapping("/getByFilter")
    public Object getByFilter(@RequestBody Map<String, Object> queryParams) {
        return this.baseService.getByFilter(queryParams);
    }

    @Operation(summary = "导出查询结果为excel")
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody QueryParams<Map<String, Object>> queryParams) {
        this.baseService.exportExcel(queryParams, response);
    }

}

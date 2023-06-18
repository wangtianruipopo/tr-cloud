package io.github.tr.common.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.tr.common.base.exception.CheckEntityResult;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.web.service.IBaseService;
import io.github.tr.common.web.utils.ModelUtil;
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

    @PostMapping("/save")
    public T save(@RequestBody Map<String, Object> entity) {
        return this.baseService.saveEntity(entity);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        this.baseService.delete(id);
    }

    @DeleteMapping("/deleteBatch")
    public void deleteBatch(@RequestBody List<Serializable> ids) {
        this.baseService.deleteBatch(ids);
    }

    @PostMapping("/query")
    public IPage<?> query(@RequestBody QueryParams<Map<String, Object>> queryParams) {
        return this.baseService.query(queryParams);
    }

    @GetMapping("/get/{id}")
    public Object get(@PathVariable("id") Serializable id) {
        return this.baseService.get(id);
    }

    @PostMapping("/getByFilter")
    public Object getByFilter(@RequestBody Map<String, Object> queryParams) {
        return this.baseService.getByFilter(queryParams);
    }

    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody QueryParams<Map<String, Object>> queryParams) {
//        this.baseService.exportExcel(queryParams, response);
        // TODO
        System.out.println(queryParams);
    }

}

package io.github.tr.common.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.web.utils.CheckEntityResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseService<T> extends IService<T> {
    /**
     * 保存
     * @param entity 保存的对象
     */
    void saveEntity(T entity);

    T saveEntity(Map<String, Object> entity);

    /**
     * 更新之前的校验函数
     * @param entity 更新对象
     * @param id 更新前的旧数据对象id
     * @param checkEntityResult 校验结果
     */
    void beforeUpdate(T entity, Serializable id, CheckEntityResult checkEntityResult);

    /**
     * 插入前的校验函数
     * @param entity 插入对象
     * @param checkEntityResult 校验结果
     */
    CheckEntityResult beforeInsert(T entity, CheckEntityResult checkEntityResult);

    void afterUpdate(T entity);

    void afterInsert(T entity);

    IPage<?> query(QueryParams<Map<String, Object>> queryParams);

    IPage<?> queryMapper(Page<T> page, Map<String, Object> p);

    Object get(Serializable id);

    Object extensionOne(T t);

    Object getByFilter(Map<String, Object> queryParams);

    void delete(Serializable id);

    boolean beforeDelete(Serializable id);

    void afterDelete(Serializable id);

    void deleteBatch(List<Serializable> ids);

}

package io.github.tr.common.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.tr.common.base.exception.CheckEntityResult;
import io.github.tr.common.base.query.OrderByColumn;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.web.utils.IQueryFunction;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <h1>公共Service接口</h1>
 * @param <T>
 */
public interface IBaseService<T> extends IService<T> {
    /**
     * <h2>保存</h2>
     * @param entity 保存的对象
     */
    void saveEntity(T entity);

    List<T> saveBatchEntity(List<Map<String, Object>> entityList);

    T saveEntity(Map<String, Object> entity);

    /**
     * <h2>更新之前的校验函数</h2>
     * @param entity 更新对象
     * @param id 更新前的旧数据对象id
     * @param checkEntityResult 校验结果
     */
    void beforeUpdate(T entity, Serializable id, CheckEntityResult checkEntityResult);

    /**
     * <h2>插入前的校验函数</h2>
     * @param entity 插入对象
     * @param checkEntityResult 校验结果
     */
    void beforeInsert(T entity, CheckEntityResult checkEntityResult);

    /**
     * <h2>更新之后的回调函数</h2>
     * @param entity 更新对象
     */
    void afterUpdate(T entity);

    /**
     * <h2>插入之后的回调函数</h2>
     * @param entity 插入对象
     */
    void afterInsert(T entity);

    /**
     * <h2>删除之前的校验函数</h2>
     * @param id 被删除对象的主键
     * @param checkEntityResult 校验结果
     */
    void beforeDelete(Serializable id, CheckEntityResult checkEntityResult);

    /**
     * <h2>删除之后的回调函数</h2>
     * @param id 被删除对象的主键
     */
    void afterDelete(Serializable id);

    /**
     * <h2>页面查询方法</h2>
     * @param queryParams 查询参数
     * @return 查询结果集及分页参数的包装对象
     */
    IPage<?> query(QueryParams<Map<String, Object>> queryParams);

    IPage<?> query(QueryParams<Map<String, Object>> queryParams, IQueryFunction<T> fn);

    /**
     * <h2>与数据库交互的查询页面逻辑</h2>
     * <p>配合 IPage<?> query(QueryParams<Map<String, Object>> queryParams) 方法完成页面查询逻辑</p>
     * @param page 分页条件
     * @param p 查询条件
     * @return 查询结果集及分页参数的包装对象
     */
    IPage<?> queryMapper(Page<T> page, Map<String, Object> p, List<OrderByColumn> order);

    /**
     * <h2>根据主键id获取对象信息</h2>
     * @param id 主键id
     * @return 查询结果
     */
    Object get(Serializable id);

    /**
     * <h2>对单条查询结果进行扩展操作</h2>
     * @param t 查询后的单条结果
     * @return 扩展后的查询结果
     */
    Object extensionOne(T t);

    /**
     * <h2>根据条件获取单条信息</h2>
     * @param queryParams 条件参数
     * @return 查询结果
     */
    Object getByFilter(Map<String, Object> queryParams);

    /**
     * <h2>根据主键删除单条信息</h2>
     * @param id 主键
     */
    void delete(Serializable id);

    /**
     * <h2>批量删除</h2>
     * @param ids 批量删除的主键集合
     */
    void deleteBatch(List<Serializable> ids);

    /**
     * <h2>Excel 导出方法</h2>
     * @param queryParams 导出的结果集查询条件
     * @param response http的response对象
     */
    void exportExcel(QueryParams<Map<String, Object>> queryParams, HttpServletResponse response);
}

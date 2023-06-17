package io.github.tr.common.web.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.web.exception.CheckEntityException;
import io.github.tr.common.web.service.IBaseService;
import io.github.tr.common.web.utils.CheckEntityResult;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Autowired
    BeanUtilsBean beanUtilsBean;


    @Override
    @Transactional
    public void saveEntity(T entity) {
        Serializable key = null;
        if (entity instanceof Model<?>) {
            Model<?> model = (Model<?>) entity;
            key = model.pkVal();
        } else {
            // 去判断是否有注解
            TableId tableId = entity.getClass().getAnnotation(TableId.class);
            Field[] fields = ReflectUtil.getFields(entity.getClass(), f -> f.getAnnotation(TableId.class) != null);
            Assert.notNull(fields);
            if (fields.length == 1) {
                key = (Serializable) ReflectUtil.getFieldValue(entity, fields[0]);
            }
        }
        CheckEntityResult checkRes = new CheckEntityResult();
        if (key != null) {
            this.beforeUpdate(entity, key, checkRes);
        } else {
            this.beforeInsert(entity, checkRes);
        }
        if (checkRes.passed()) {
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
    public T saveEntity(Map<String, Object> entity) {
        // 获取实体类类型
//        Class<T> entityType = getEntityType();
        Class<?> entityType = ClassUtil.getTypeArgument(this.getClass(), 1);
//        Class<T> entityType = (Class<T>) ReflectUtil.getEntityType(this.getClass(), 1);
        if (entityType == null) throw new NullPointerException("获取实体类类型异常！");
//        Object instance = entityType.getDeclaredConstructor().newInstance();
//        beanUtilsBean.populate(instance, entity);
//        saveEntity(instance);
//        return instance;
        return null;
    }

    @Override
    public void beforeUpdate(T entity, Serializable id, CheckEntityResult checkEntityResult) {

    }

    @Override
    public CheckEntityResult beforeInsert(T entity, CheckEntityResult checkEntityResult) {
        return null;
    }

    @Override
    public void afterUpdate(T entity) {

    }

    @Override
    public void afterInsert(T entity) {

    }

    @Override
    public IPage<?> query(QueryParams<Map<String, Object>> queryParams) {
        return null;
    }

    @Override
    public IPage<?> queryMapper(Page<T> page, Map<String, Object> p) {
        return null;
    }

    @Override
    public Object get(Serializable id) {
        return null;
    }

    @Override
    public Object extensionOne(T t) {
        return null;
    }

    @Override
    public Object getByFilter(Map<String, Object> queryParams) {
        return null;
    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public boolean beforeDelete(Serializable id) {
        return false;
    }

    @Override
    public void afterDelete(Serializable id) {

    }

    @Override
    public void deleteBatch(List<Serializable> ids) {

    }
}

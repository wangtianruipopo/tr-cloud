package io.github.tr.common.web.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.tr.common.base.query.QueryParams;
import io.github.tr.common.web.exception.CheckEntityException;
import io.github.tr.common.web.service.IBaseService;
import io.github.tr.common.web.utils.CheckEntityResult;
import io.github.tr.common.web.utils.ModelUtil;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
    public IPage<?> query(QueryParams<Map<String, Object>> queryParams) {
        // 获取查询参数
        Map<String, Object> p = queryParams.getFilter();
        int pageIndex = queryParams.getPageIndex();
        int pageSize = queryParams.getPageSize();
        Page<T> page = new Page<>();
        page.setCurrent(pageIndex);
        page.setSize(pageSize);
        return this.queryMapper(page, p);
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
        if (checkRes.passed()) {
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
            if (checkRes.passed()) {
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

    private Object getOneCommon(T t) {
        // 进行扩展处理
        Object res = this.extensionOne(t);
        if (res == null) {
            return t;
        }
        return res;
    }
}

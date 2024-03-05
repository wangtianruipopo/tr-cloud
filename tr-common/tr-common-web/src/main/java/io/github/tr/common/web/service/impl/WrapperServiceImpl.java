package io.github.tr.common.web.service.impl;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.tr.common.base.exception.CheckEntityResult;
import io.github.tr.common.base.query.OrderByColumn;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class WrapperServiceImpl<M extends BaseMapper<T>, T> extends BaseServiceImpl<M, T>{
    @Override
    public void beforeUpdate(T entity, Serializable id, CheckEntityResult checkEntityResult) {}

    @Override
    public void beforeInsert(T entity, CheckEntityResult checkEntityResult) {}

    @Override
    public void afterUpdate(T entity) {}

    @Override
    public void afterInsert(T entity) {}

    @Override
    public void beforeDelete(Serializable id, CheckEntityResult checkEntityResult) {}

    @Override
    public void afterDelete(Serializable id) {}

    @Override
    @SneakyThrows
    public IPage<?> queryMapper(Page<T> page, Map<String, Object> p, List<OrderByColumn> order) {
        Method query = ClassUtil.getDeclaredMethod(this.baseMapper.getClass(), "query", page.getClass(), p.getClass());
        if (query != null) {
            return (IPage<?>) query.invoke(this.baseMapper, page, p, order);
        }
        return null;
    }

    @Override
    public Object extensionOne(T t) {
        return null;
    }
}

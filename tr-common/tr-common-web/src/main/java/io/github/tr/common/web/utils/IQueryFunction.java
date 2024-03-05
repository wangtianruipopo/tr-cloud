package io.github.tr.common.web.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.tr.common.base.query.OrderByColumn;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface IQueryFunction<T> {
    IPage<?> apply(Page<T> page, Map<String, Object> p, List<OrderByColumn> order);
}

package io.github.tr.common.base.query;

import java.util.List;

@FunctionalInterface
public interface QueryFunction<T> {
    List<T> data(QueryParams<?> params);
}

package io.github.tr.common.base.query;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface QueryFunction<T> {
    List<T> data(QueryParams<Map<String, Object>> params);
}

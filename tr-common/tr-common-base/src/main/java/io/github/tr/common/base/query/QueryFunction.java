package io.github.tr.common.base.query;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface QueryFunction {
    List<?> data(QueryParams<Map<String, Object>> params);
}

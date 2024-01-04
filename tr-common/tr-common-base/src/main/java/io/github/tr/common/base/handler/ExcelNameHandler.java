package io.github.tr.common.base.handler;

import io.github.tr.common.base.query.QueryParams;

import java.util.Map;

public interface ExcelNameHandler {
    String fileName(QueryParams<Map<String, Object>> queryParams);
    String sheetName(QueryParams<Map<String, Object>> queryParams);
}

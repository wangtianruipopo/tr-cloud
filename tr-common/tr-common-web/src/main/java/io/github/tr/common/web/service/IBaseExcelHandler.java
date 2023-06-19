package io.github.tr.common.web.service;

import com.alibaba.excel.write.handler.WriteHandler;
import io.github.tr.common.base.query.QueryParams;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBaseExcelHandler {
    default Collection<WriteHandler> writeHandler(QueryParams<Map<String, Object>> params) {
        return null;
    }

    default Collection<Integer> columnsByIndex(QueryParams<Map<String, Object>> params) {
        return null;
    }
    default Collection<String> columnsByName(QueryParams<Map<String, Object>> params) {
        return null;
    }
}

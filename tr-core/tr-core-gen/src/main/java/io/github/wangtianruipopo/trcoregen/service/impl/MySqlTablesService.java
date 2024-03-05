package io.github.wangtianruipopo.trcoregen.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.tr.common.base.query.OrderByColumn;
import io.github.wangtianruipopo.trcoregen.entity.Columns;
import io.github.wangtianruipopo.trcoregen.entity.Schema;
import io.github.wangtianruipopo.trcoregen.entity.Tables;

import java.util.List;
import java.util.Map;

public class MySqlTablesService extends AbsTablesServiceImpl {

    @Override
    public IPage<?> dynamicQuery(Page<Tables> page, Map<String, Object> p, List<OrderByColumn> order) {
        return this.baseMapper.mysqlAllTables(page, p, order);
    }

    @Override
    public List<Columns> listColumn(String schema, String tableName) {
        return this.columnsMapper.listMysqlByTableName(schema, tableName);
    }

    @Override
    public List<Schema> listSchema() {
        return this.baseMapper.listSchema();
    }
}

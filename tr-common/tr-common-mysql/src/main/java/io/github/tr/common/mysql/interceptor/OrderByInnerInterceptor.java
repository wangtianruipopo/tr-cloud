package io.github.tr.common.mysql.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import io.github.tr.common.base.query.OrderByColumn;
import io.github.tr.common.mysql.constant.MybatisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Slf4j
public class OrderByInnerInterceptor implements InnerInterceptor {
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        if (SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
            System.out.println("Intercepted SELECT statement: " + boundSql.getSql());
            if (parameter == null) return;
            if (!(parameter instanceof MapperMethod.ParamMap)) return;
            MapperMethod.ParamMap<?> p = (MapperMethod.ParamMap<?>) parameter;

            if (p.containsKey(MybatisConstant.ORDER_NAME)) {
                @SuppressWarnings("unchecked")
                List<OrderByColumn> order = (List<OrderByColumn>) p.get(MybatisConstant.ORDER_NAME);
                if (order == null || order.isEmpty()) return;
                StringBuilder sql = new StringBuilder(boundSql.getSql());
                sql = new StringBuilder("select * from (" + sql + ") as t order by ");
                for (int i = 0; i < order.size(); i++) {
                    OrderByColumn orderByColumn = order.get(i);
                    String o = orderByColumn.getName() + " " + orderByColumn.getType();
                    if (i == order.size() - 1) {
                        sql.append(o);
                    } else {
                        sql.append(o).append(", ");
                    }
                }
                PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
                mpBoundSql.sql(sql.toString());
            }
        }
    }
}

package io.github.tr.common.base.query;

import lombok.Data;

import java.util.List;

/**
 * 分页查询参数
 * @param <P>
 * @author wangtianrui
 */
@Data
public class QueryParams<P> {
    private Integer pageIndex;
    private Integer pageSize;
    private P filter;
    private List<OrderByColumn> order;
}

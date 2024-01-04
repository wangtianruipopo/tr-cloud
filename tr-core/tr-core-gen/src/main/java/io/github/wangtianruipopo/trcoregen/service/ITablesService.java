package io.github.wangtianruipopo.trcoregen.service;

import io.github.tr.common.web.service.IBaseService;
import io.github.wangtianruipopo.trcoregen.entity.CreateCodeFilter;
import io.github.wangtianruipopo.trcoregen.entity.Schema;
import io.github.wangtianruipopo.trcoregen.entity.Tables;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ITablesService extends IBaseService<Tables> {
    void createCode(CreateCodeFilter filter, HttpServletResponse response);

    List<Schema> listSchema();
}

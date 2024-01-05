package io.github.wangtianruipopo.trcoregen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.wangtianruipopo.trcoregen.entity.Schema;
import io.github.wangtianruipopo.trcoregen.entity.Tables;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TablesMapper extends BaseMapper<Tables> {
    List<Schema> listSchema();
    List<Schema> listPgSchema();
    IPage<Tables> mysqlAllTables(Page<Tables> page, @Param("params") Map<String, Object> params);
    IPage<Tables> pgsqlAllTables(Page<Tables> page, @Param("params") Map<String, Object> params);
}

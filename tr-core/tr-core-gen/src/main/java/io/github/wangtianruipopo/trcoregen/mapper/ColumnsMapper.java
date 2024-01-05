package io.github.wangtianruipopo.trcoregen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.wangtianruipopo.trcoregen.entity.Columns;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ColumnsMapper extends BaseMapper<Columns> {
    List<Columns> listMysqlByTableName(@Param("schema") String schema, @Param("tableName") String tableName);
    List<Columns> listPgSqlByTableName(@Param("schema") String schema, @Param("tableName") String tableName);
}

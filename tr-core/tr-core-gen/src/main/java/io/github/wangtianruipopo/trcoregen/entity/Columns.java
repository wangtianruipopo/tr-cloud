package io.github.wangtianruipopo.trcoregen.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class Columns {
    private String tableSchema;
    private String tableName;
    private String columnName;
    private String isNullable;
    private String dataType;
    private Long characterMaximumLength;
    private Long characterOctetLength;
    private Long numericPrecision;
    private Long numericScale;
    private String columnComment;
    private String columnKey;

    public boolean isPk() {
        return "PRI".equals(this.columnKey) || "PRIMARY KEY".equals(this.columnKey);
    }
    public String getJavaType() {
        String type = this.dataType;
        if ("varchar".equals(type) || "text".equals(type) || "longtext".equals(type)) return "String";
        if ("int".equals(type)) return "Integer";
        if ("decimal".equals(type)) return "Double";
        if ("timestamp".equals(type) || "datetime".equals(type)) {
            return "DateTime";
        }
        if ("date".equals(type)) return "Date";
        if ("tinyint".equals(type)) return "Boolean";
        if ("numeric".equals(type)) {
            return this.getNumericScale() > 0 ? "Double" : "Integer";
        }
        return "String";
    }

    public String getName() {
        return StrUtil.toCamelCase(this.columnName);
    }
}

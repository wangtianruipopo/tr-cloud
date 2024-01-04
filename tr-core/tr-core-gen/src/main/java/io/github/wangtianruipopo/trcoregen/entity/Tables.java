package io.github.wangtianruipopo.trcoregen.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Tables {
    private String tableSchema;
    private String tableName;
    private String tableComment;
    private Set<String> importList;
    private List<Columns> columns;
    private String packageName;

    public String getClassName() {
        return StrUtil.upperFirst(StrUtil.toCamelCase(this.tableName));
    }

    public void addColumns(List<Columns> columns) {
        this.columns = columns;
        this.importList = new HashSet<>();
        this.columns.forEach(column -> {
            String type = column.getJavaType();
            if ("Date".equals(type)) {
                this.importList.add("java.time.LocalDate");
            } else if ("DateTime".equals(type)) {
                this.importList.add("java.time.LocalDateTime");
            } else if ("Double".equals(type)) {
                this.importList.add("java.time.LocalDate");
            }
        });
    }

    public String getControllerName() {
        return StrUtil.toCamelCase(this.tableName);
    }

}

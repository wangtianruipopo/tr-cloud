package io.github.wangtianruipopo.trcoregen.entity;

import lombok.Data;

import java.util.List;

@Data
public class CreateCodeFilter {
    private List<Tables> tableList;
    private String entityPath;
    private String mapperPath;
    private String servicePath;
    private String controllerPath;
    private boolean containExport;
}

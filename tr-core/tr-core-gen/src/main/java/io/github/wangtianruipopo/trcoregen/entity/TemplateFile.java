package io.github.wangtianruipopo.trcoregen.entity;

import lombok.Builder;
import lombok.Data;

import java.util.function.Function;

@Data
@Builder
public class TemplateFile {
    private String templatePath;
    private String prefix;
    private String suffix;
    private Function<String, String> func;

    public String getName(String className) {
        return this.func.apply(className);
    }

}

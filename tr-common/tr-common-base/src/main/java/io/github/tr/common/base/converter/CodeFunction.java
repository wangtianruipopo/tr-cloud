package io.github.tr.common.base.converter;

@FunctionalInterface
public interface CodeFunction {
    String getCode(Object entity);
}

package io.github.tr.common.base.converter;

public interface TransCode {
    /**
     * 根据字典值和字典代码获取字典文本
     * @param value 字典值
     * @param code 字典代码
     * @param multipart 是否多选
     * @return 字典对应的文本
     */
    String getText(Object value, String code, boolean multipart);
}

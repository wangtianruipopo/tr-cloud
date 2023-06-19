package io.github.tr.common.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReflectUtil {

    public static <T> Object getValueByMethod(T item, Field f) throws Exception {
        // 获取字段名称
        String fieldName = f.getName();
        // 首字母大写
        StringBuilder methodName = new StringBuilder("get");
        char[] array = fieldName.toCharArray();
        for (int i = 0; i < array.length; i++) {
            String r = String.valueOf(array[i]);
            if (i == 0) {
                r = r.toUpperCase();
            }
            methodName.append(r);
        }
        Method method = item.getClass().getMethod(methodName.toString());
        return method.invoke(item);
    }
}

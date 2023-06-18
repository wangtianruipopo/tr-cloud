package io.github.tr.common.web.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ModelUtil {
    public static Serializable getKey(Object entity) {
        Serializable key = null;
        if (entity instanceof Model<?>) {
            Model<?> model = (Model<?>) entity;
            key = model.pkVal();
        } else {
            // 去判断是否有注解
            Field[] fields = ReflectUtil.getFields(entity.getClass(), f -> f.getAnnotation(TableId.class) != null);
            Assert.notNull(fields);
            if (fields.length == 1) {
                key = (Serializable) ReflectUtil.getFieldValue(entity, fields[0]);
            }
        }
        return key;
    }
}

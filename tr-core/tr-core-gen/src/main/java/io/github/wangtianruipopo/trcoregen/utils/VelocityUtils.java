package io.github.wangtianruipopo.trcoregen.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.wangtianruipopo.trcoregen.entity.CreateCodeFilter;
import io.github.wangtianruipopo.trcoregen.entity.Tables;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>模板工具类</h1>
 *
 * @author wangtianrui
 */
public class VelocityUtils {

    /**
     * <h2>设置模板变量信息</h2>
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(Tables table, CreateCodeFilter filter) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("table", table);
        velocityContext.put("filter", filter);
        return velocityContext;
    }
}

package io.github.tr.common.mysql.config;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;

import java.util.List;

public interface InnerInterceptorContainer {
    List<InnerInterceptor> innerInterceptors();
}

package io.github.tr.common.web.config;

import com.alibaba.fastjson2.JSON;
import io.github.tr.common.base.annotation.IgnoreResponseAdvice;
import io.github.tr.common.base.http.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * <h1>实现统一响应</h1>
 *
 * @author 王天瑞
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    /**
     * <h2>判断是否需要对响应进行处理</h2>
     *
     * @param methodParameter 请求方法对象
     * @param converterType   处理类
     * @return 是否需要进行响应处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        boolean springfox = methodParameter.getDeclaringClass().getName().contains("springfox");
        if (springfox) {
            // 如果是swagger请求，则不进行响应处理
            return false;
        }
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        Method method = methodParameter.getMethod();
        Class<IgnoreResponseAdvice> type = IgnoreResponseAdvice.class;
        return !(declaringClass.isAnnotationPresent(type) || Objects.requireNonNull(method).isAnnotationPresent(type));
    }

    /**
     * <h2>对请求响应结果进行封装</h2>
     *
     * @param body                  原生返回数据值
     * @param returnType            返回类型
     * @param selectedContentType   用不到
     * @param selectedConverterType 用不到
     * @param request               用不到
     * @param response              用不到
     * @return 请求响应结果
     */
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        HttpResult<Object> result = HttpResult.ok();
        if (body instanceof HttpResult) {
            return body;
        }
        result.setData(body);
        if (body instanceof String) {
            return JSON.toJSONString(result);
        }
        return result;
    }

}

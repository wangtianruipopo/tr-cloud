package io.github.tr.common.base.http;

import lombok.Data;

/**
 * http请求响应结果
 * @param <R> 返回响应类型
 * @author wangtianrui
 */
@Data
public class HttpResult<R> {

    private boolean ok;
    private String code;
    private String message;
    private R data;

    private HttpResult(){}

    /**
     * 成功无返回响应
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> ok(){
        return ok(null);
    }

    /**
     * 成功有返回响应
     * @param data 响应数据
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> ok(T data){
        return createHttpResponseData(HttpResponseStatusEnum.OK.getCode(), HttpResponseStatusEnum.OK.getMessage(), data);
    }

    /**
     * 错误无描述且默认代码响应
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> error(){
        return error(HttpResponseStatusEnum.SYS_ERROR.getMessage());
    }

    /**
     * 错误有描述且默认代码响应
     * @param message 错误描述
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> error(String message){
        return error(HttpResponseStatusEnum.SYS_ERROR.getCode(), message);
    }

    /**
     * 错误有描述且有代码响应
     * @param code 错误代码
     * @param message 错误描述
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> error(String code, String message){
        return createHttpResponseData(code, message, false, null);
    }

    public static <T> HttpResult<T> createHttpResponseData(String code, String message, T data){
        return createHttpResponseData(code, message, true, data);
    }

    /**
     * 根据响应枚举返回错误信息
     * @param status 响应枚举
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> error(IResponseStatus status){
        return error(status, status.getMessage());
    }

    /**
     * 根据响应枚举且自定义错误描述返回错误信息
     * @param status 响应枚举
     * @param message 错误描述
     * @return http响应结果
     * @param <T> 响应类型
     */
    public static <T> HttpResult<T> error(IResponseStatus status, String message){
        return  createHttpResponseData(status.getCode(), message, false, null);
    }

    public static <T> HttpResult<T> createHttpResponseData(String code, String message, Boolean ok, T data){
        HttpResult<T> rd = new HttpResult<>();
        rd.code = code;
        rd.message = message;
        rd.ok = ok;
        rd.data = data;
        return rd;
    }
}

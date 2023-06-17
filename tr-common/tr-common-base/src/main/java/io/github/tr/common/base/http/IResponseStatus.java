package io.github.tr.common.base.http;

public interface IResponseStatus {
    String getCode();

    void setCode(String code);

    String getMessage();

    void setMessage(String message);
}

/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.tr.common.web.exception;

import io.github.tr.common.base.http.HttpResult;
import io.github.tr.common.web.utils.SingleCheckEntityResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static io.github.tr.common.base.http.HttpResponseStatusEnum.REQUEST_PARAM_ERROR;
import static io.github.tr.common.base.http.HttpResponseStatusEnum.SYS_ERROR;

/**
 * 全局异常类
 *
 * @author wangtianrui
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{

    /**
     * 处理所有不可知异常
     */
    @ExceptionHandler(value = Throwable.class)
    public HttpResult<Object> unknownException(Throwable e){
        log.error("全局异常处理!", e);
        return HttpResult.error(SYS_ERROR, e.getMessage());
    }

    /**
     * 处理校验字段失败异常
     * @param e 校验失败异常对象
     * @return 异常处理结果
     */
    @ExceptionHandler(value = CheckEntityException.class)
    public HttpResult<List<SingleCheckEntityResult>> checkEEntityException(CheckEntityException e){
        log.error("字段校验失败!", e);
        HttpResult<List<SingleCheckEntityResult>> error = HttpResult.error(REQUEST_PARAM_ERROR);
        error.setData(e.getResult());
        return error;
    }
}
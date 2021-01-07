package com.liuyun.auth.config.handler;

import com.liuyun.auth.config.exception.AuthOauth2Exception;
import com.liuyun.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/28 01:06
 **/
@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({AuthOauth2Exception.class})
    public Result<String> authOauth2Exception(AuthOauth2Exception e) {
        log.info(e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

}

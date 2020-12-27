package com.liuyun.auth.config.handler;

import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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

    /**
     * AccessDeniedException异常处理返回json
     * 返回状态码:403
     */
    @ExceptionHandler({AccessDeniedException.class})
    public Result<String> badMethodExpressException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return Result.fail(HttpStatus.FORBIDDEN.value(), "没有权限访问该资源!!!");
    }

}

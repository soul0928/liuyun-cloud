package com.liuyun.web.exception;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.global.exception.GlobalException;
import com.liuyun.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 异常处理器
 * @author wangdong
 * @version 1.0.0
 * @date 2020/7/27 20:17
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private GlobalExceptionHandler() {}

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GlobalException.class)
    public Result<String> globalException(GlobalException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * Valid 参数校验异常拦截
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return Result.fail(GlobalResultEnum.USER_REQUEST_PARAM_ERROR.getCode(), getError(e.getBindingResult().getAllErrors()));
    }

    private String getError(List<ObjectError> allErrors) {
        StringBuilder message = new StringBuilder();
        for(ObjectError error: allErrors){
            message.append(error.getDefaultMessage()).append(" & ");
        }
        // 因为&两边空格
        return message.substring(0, message.length() - 3);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Result.fail(GlobalResultEnum.USER_ERROR.getCode(), "不支持该请求方法");
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage() , e);
        return Result.fail();
    }
}

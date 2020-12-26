package com.liuyun.core.exception;

import com.liuyun.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

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
     * 处理自定义异常
     */
    @ExceptionHandler(AuthException.class)
    public Result<String> authException(AuthException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> bindException(BindException e) {
        log.error(e.getMessage(), e);
        return Result.fail(getError(e.getBindingResult().getAllErrors()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return Result.fail(getError(e.getBindingResult().getAllErrors()));
    }

    private String getError(List<ObjectError> allErrors) {
        StringBuilder message = new StringBuilder();
        for(ObjectError error: allErrors){
            message.append(error.getDefaultMessage()).append(" & ");
        }
        // 因为&两边空格
        return message.substring(0, message.length() - 3);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<String> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return Result.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage() , e);
        return Result.fail();
    }
}

package com.liuyun.core.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * 认证异常类
 * @author wangdong
 * @date 2020/7/27 8:16 下午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code;

    public AuthException(String msg) {
        super(msg);
        this.code = HttpStatus.UNAUTHORIZED.value();
        this.msg = msg;
    }

    public AuthException(String msg, Throwable e) {
        super(msg, e);
        this.code = HttpStatus.UNAUTHORIZED.value();
        this.msg = msg;
    }

    public AuthException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public AuthException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}

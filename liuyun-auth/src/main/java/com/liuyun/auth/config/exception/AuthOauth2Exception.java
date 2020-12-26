package com.liuyun.auth.config.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 登录异常类
 *
 * @author wangdong
 * @date 2020/7/27 8:16 下午
 **/
@JsonSerialize(using = AuthOauth2ExceptionJacksonSerializer.class)
public class AuthOauth2Exception extends OAuth2Exception {

    private static final long serialVersionUID = 1L;

    public AuthOauth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthOauth2Exception(String msg) {
        super(msg);
    }

}

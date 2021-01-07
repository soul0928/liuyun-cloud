package com.liuyun.auth.config.exception;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录异常类
 *
 * @author wangdong
 * @date 2020/7/27 8:16 下午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthOauth2Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    public AuthOauth2Exception(GlobalResultEnum globalResultEnum) {
        super(globalResultEnum.getDesc());
        this.code = globalResultEnum.getCode();
    }

    public AuthOauth2Exception(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public AuthOauth2Exception(String code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }
}

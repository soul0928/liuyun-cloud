package com.liuyun.core.exception;

import com.liuyun.core.global.enums.GlobalResultEnum;

/**
 * 登录异常类
 * @author wangdong
 * @date 2020/7/27 8:16 下午
 **/
public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private Integer code = GlobalResultEnum.FAIL.getCode();

    public LoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LoginException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public LoginException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public LoginException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
}

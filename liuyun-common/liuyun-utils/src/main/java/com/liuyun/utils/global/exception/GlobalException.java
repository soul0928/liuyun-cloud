package com.liuyun.utils.global.exception;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义全局异常类
 * @author wangdong
 * @date 2020/7/27 8:16 下午
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code = GlobalResultEnum.FAIL.getCode();
    private String msg;

    public GlobalException() {
        super(GlobalResultEnum.FAIL.getDesc());
        this.msg = GlobalResultEnum.FAIL.getDesc();
    }

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GlobalException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }
    
}

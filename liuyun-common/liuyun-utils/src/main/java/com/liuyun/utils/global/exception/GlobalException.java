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

    private String code;
    private String msg;

    public GlobalException() {
        super(GlobalResultEnum.SYSTEM_EXECUTION_ERROR.getDesc());
        this.code = GlobalResultEnum.SYSTEM_EXECUTION_ERROR.getCode();
        this.msg = GlobalResultEnum.SYSTEM_EXECUTION_ERROR.getDesc();
    }

    public GlobalException(String msg) {
        super(msg);
        this.code = GlobalResultEnum.SYSTEM_EXECUTION_ERROR.getCode();
        this.msg = msg;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.code = GlobalResultEnum.SYSTEM_EXECUTION_ERROR.getCode();
        this.msg = msg;
    }

    public GlobalException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }
    
}

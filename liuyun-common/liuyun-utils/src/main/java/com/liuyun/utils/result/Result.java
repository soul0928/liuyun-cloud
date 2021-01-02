package com.liuyun.utils.result;

import com.liuyun.utils.global.enums.GlobalResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * Result
 * 统一返回参数
 *
 * @author WangDong
 * @date 2020/6/1 3:39 下午
 * @version 1.0.0
 **/
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8031438847417769246L;

    public static final String MSG = GlobalResultEnum.SUCCESS.getDesc();

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 返回处理消息
     */
    private String message;

    /**
     * 返回数据对象 data
     */
    private T result;

    public Result() {
        this.code = GlobalResultEnum.SUCCESS.getCode();
        this.message = MSG;
        this.result = null;
    }

    public static <T> Result<T> success() {
        return new Result<T>();
    }

    public static <T> Result<T> success(int code) {
        Result<T> r = new Result<>();
        r.setCode(code);
        return r;
    }

    public static <T> Result<T> success(String message) {
        Result<T> r = new Result<>();
        r.setMessage(message);
        return r;
    }

    public static <T> Result<T> success(T t) {
        Result<T> r = new Result<>();
        r.setResult(t);
        return r;
    }

    public static <T> Result<T> success(String message, T t) {
        return success(GlobalResultEnum.SUCCESS.getCode(), message, t);
    }

    public static <T> Result<T> success(Integer code, String message, T t) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(message);
        r.setResult(t);
        return r;
    }

    public static <T> Result<T> fail() {
        return fail(GlobalResultEnum.FAIL.getDesc());
    }

    public static <T> Result<T> fail(String message) {
        return fail(GlobalResultEnum.FAIL.getCode(), message);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return fail(code, msg, null);
    }

    public static <T> Result<T> fail(String msg, T t) {
        return fail(GlobalResultEnum.FAIL.getCode(), msg, t);
    }

    public static <T> Result<T> fail(Integer code, String msg, T t) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setResult(t);
        return r;
    }
}

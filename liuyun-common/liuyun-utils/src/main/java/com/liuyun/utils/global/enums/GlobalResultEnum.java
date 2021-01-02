package com.liuyun.utils.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  GlobalEnums
 * 全局响应状态
 * @author WangDong
 * @date 2020/6/1 3:46 下午
 * @version 1.0.0
 **/
@Getter
@AllArgsConstructor
public enum GlobalResultEnum {

    /**
     * 系统执行成功
     */
    SUCCESS(200,  "处理成功!!!"),
    /**
     * 系统执行失败
     */
    FAIL(500, "系统异常, 请联系管理员!!!"),
    ;

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;


}

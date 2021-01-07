package com.liuyun.model.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帐号状态
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/25 10:39
 **/
@Getter
@AllArgsConstructor
public enum SysUserStatusEnum {

    /**
     * 帐号状态(100:启用; 200:禁用)
     */
    ENABLE(100),
    DISABLE(200),
    ;

    private final int code;

}

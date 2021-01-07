package com.liuyun.model.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限类型(100:菜单; 200:按钮; 300:接口)
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/3 15:50
 **/
@Getter
@AllArgsConstructor
public enum SysPermissionTypeEnum {

    /**
     * 权限类型(100:菜单; 200:按钮; 300:接口)
     */
    MENU(100),
    BUTTON(200),
    URL(300),
    ;
    private final int code;

}

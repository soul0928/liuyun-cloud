package com.liuyun.utils.global.enums;

import com.liuyun.utils.lang.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 服务枚举
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/2 21:18
 **/
@Getter
@AllArgsConstructor
public enum GlobalServiceEnum {

    /**
     * 服务枚举
     */
    LIUYUN_USER("liuyun-user","用户服务"),
    LIUYUN_AUTH("liuyun-auth","授权服务"),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;

    public static String getDesc(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (GlobalServiceEnum value : GlobalServiceEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value.desc;
            }
        }
        return null;
    }
}

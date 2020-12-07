package com.liuyun.log.logback;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志埋点工具类
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/5 20:08
 **/
@Slf4j
public class PointUtil {

    public static final String SPLIT = "|";

    private PointUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * 格式为：{时间}|{来源}|{对象id}|{类型}|{对象属性(以&分割)}
     * 例子1：2020-12-04 23:37:23|business-center|1|ums-login|ip=xxx.xxx.xx&userName=张三&userType=后台管理员
     * 例子2：2020-12-04 23:37:23|file-center|c0a895e114526786450161001d1ed9|file-upload|fileName=xxx&filePath=xxx
     *
     * @param id {@link String} 对象id
     * @param type {@link String} 类型
     * @param message {@link String}  对象属性
     * @author wangdong
     * @date 2020/12/5 8:10 下午
     **/
    public static void info(String id, String type, String message) {
        log.info(id + SPLIT + type + SPLIT + message);
    }
}

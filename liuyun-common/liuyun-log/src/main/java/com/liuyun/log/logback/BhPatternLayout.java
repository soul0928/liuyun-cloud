package com.liuyun.log.logback;


import ch.qos.logback.classic.PatternLayout;

/**
 * logback属性添加
 * @author wangdong
 * @date 2020/7/31 8:27 下午
 **/
public class BhPatternLayout extends PatternLayout {

    static {
        defaultConverterMap.put("ip", IpConverter.class.getName());
    }
}

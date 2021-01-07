package com.liuyun.utils.lang;

import cn.hutool.core.util.StrUtil;

import java.util.Objects;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/4 17:49
 **/
public class StringUtils extends StrUtil {

    private static final CharSequence NULL_STR = "null";

    /**
     * 字符串是否为非空
     * 并且不等于 "null"
     *
     * @param str {@link CharSequence} 被检测的字符串
     * @return boolean
     * @author wangdong
     * @date 2021/1/4 6:02 下午
     **/
    public static boolean isNotNull(CharSequence str) {
        return !Objects.equals(NULL_STR, str) && StrUtil.isNotBlank(str);
    }


    /**
     * 检查是否以指定的后缀结尾(不区分大小写)
     *
     * @param str {@link CharSequence} 被检测的字符串
     * @param suffix {@link CharSequence}  后缀
     * @return boolean
     * @author wangdong
     * @date 2021/1/4 6:01 下午
     **/
    public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(str, suffix);
    }



}

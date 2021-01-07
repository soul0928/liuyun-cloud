package com.liuyun.utils.servlet;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/4 16:56
 **/
public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 获得请求header中的信息
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @param name 头信息的KEY
     * @return header值
     */
    public static String getHeader(HttpServletRequest request, String name) {
        return cn.hutool.extra.servlet.ServletUtil.getHeader(request, name, UTF_8);
    }

}

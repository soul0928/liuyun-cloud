package com.liuyun.oauth2.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:24
 **/
@Data
public class AuthPermitProperties {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {
            "/oauth/**",
            "/actuator/**",
            "/*/v2/api-docs",
            "/swagger/api-docs",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/api-ums/api/applet/**",
            "/api/applet/**",
            "/druid/**"
    };

    /**
     * 设置不用认证的url
     */
    private String[] httpUrls = {};

    /**
     * 设置认证后不需要判断具体权限的url，所有登录的人都能访问
     */
    private String[] menusPaths = {};

    public String[] getUrls() {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(ENDPOINTS));
        list.addAll(Arrays.asList(httpUrls));
        list.addAll(Arrays.asList(menusPaths));
        return list.toArray(new String[0]);
    }
}

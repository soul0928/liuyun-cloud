package com.liuyun.oauth2.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求权限判断service
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 22:06
 **/
public interface AuthPermissionService {

    /**
     * 判断请求是否有权限
     *
     * @param request {@link HttpServletRequest}
     * @param authentication {@link Authentication} 认证信息
     * @return boolean 是否有权限
     * @author wangdong
     * @date 2020/12/27 10:07 下午
     **/
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}

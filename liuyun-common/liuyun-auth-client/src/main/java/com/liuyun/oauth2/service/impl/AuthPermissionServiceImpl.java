package com.liuyun.oauth2.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.liuyun.oauth2.properties.AuthSecurityProperties;
import com.liuyun.oauth2.service.AuthPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 22:08
 **/
@Slf4j
@Component
public class AuthPermissionServiceImpl implements AuthPermissionService {

    @Autowired
    private AuthSecurityProperties authSecurityProperties;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        /*if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            return true;
        }*/

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //判断是否开启url权限验证
            /*if (!authSecurityProperties.getAuth().isUrlEnabled()) {
                return true;
            }*/
            //判断认证通过后，所有用户都能访问的url

            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }
            return true;
        }
        return false;
    }
}

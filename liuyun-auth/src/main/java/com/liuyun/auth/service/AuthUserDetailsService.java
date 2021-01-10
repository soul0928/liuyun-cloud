package com.liuyun.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 16:56
 **/
public interface AuthUserDetailsService extends UserDetailsService {

    /**
     * 根据手机号获取用户信息
     *
     * @param mobile {@link String} 手机号码
     * @author wangdong
     * @date 2021/1/10 8:30 下午
     * @return org.springframework.security.core.userdetails.UserDetails
     **/
    UserDetails loadUserByUserMobile(String mobile);

}

package com.liuyun.auth.config.mode.mobilepassword;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 用户手机号密码验证相关处理配置
 *
 * @author wangdong
 * @date 2021/1/10 8:47 下午
 **/
@Slf4j
@Component
public class MobilePasswordAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private MobilePasswordAuthenticationProvider mobilePasswordAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) {
        http.authenticationProvider(mobilePasswordAuthenticationProvider);
    }
}

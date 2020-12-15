package com.liuyun.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 默认密码配置
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:18
 **/
public class AuthPasswordConfig {

    /**
     * 装配BCryptPasswordEncoder用户密码的匹配
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @author wangdong
     * @date 2020/12/14 1:19 下午
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

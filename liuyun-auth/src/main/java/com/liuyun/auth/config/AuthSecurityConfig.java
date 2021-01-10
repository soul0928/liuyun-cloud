package com.liuyun.auth.config;

import com.liuyun.auth.config.properties.AuthSecurityProperties;
import com.liuyun.auth.service.AuthUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:20
 **/
@Slf4j
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties({AuthSecurityProperties.class})
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

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

    /**
     * 必须配置 调用此方法才能支持 password 模式
     * grant_type,密码模式需要AuthenticationManager支持
     *
     * @return 认证管理对象
     * @author wangdong
     * @date 2020/12/15 9:45 上午
     **/
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Security 资源配置
     *
     * @param http {@link }
     * @author wangdong
     * @date 2020/12/27 1:04 上午
     **/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/keypair/getPublicKey", "/v2/api-docs", "/actuator/**", "/oauth/captcha/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}

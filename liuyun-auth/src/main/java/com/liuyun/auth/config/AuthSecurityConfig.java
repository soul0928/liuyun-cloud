package com.liuyun.auth.config;

import com.liuyun.auth.config.constants.AuthConstants;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


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
    private AuthSecurityProperties authSecurityProperties;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

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
                .formLogin()
                .loginPage(AuthConstants.LOGIN_URL)
                // 表单登录处理的URL
                .loginProcessingUrl(AuthConstants.AUTHENTICATION_URL)
                //登录成功之后的处理
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler)

                .and()
                // 必须配置，不然OAuth2的http配置不生效----不明觉厉
                .requestMatchers()
                .antMatchers("/auth/login", "/authentication/form", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login","/authentication/form").permitAll()
                .anyRequest().authenticated()
                //禁用跨站伪造
                .and().csrf().disable();
    }
}

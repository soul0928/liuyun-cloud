package com.liuyun.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:20
 **/
@Slf4j
@Configuration
@Import({AuthPasswordConfig.class, AuthKeyStoreConfig.class})
@ConfigurationPropertiesScan({"com.liuyun.oauth2.properties"})
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 必须配置，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
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

    /**
     * 配置http访问控制
     *
     * @param http http安全配置
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .anonymous()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "*")
                .permitAll();
    }

}

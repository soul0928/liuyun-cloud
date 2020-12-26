package com.liuyun.auth.config;

import com.liuyun.oauth2.properties.AuthSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Resource服务配置类
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/22 17:44
 **/
@Slf4j
@Configuration
@EnableResourceServer
public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthSecurityProperties authSecurityProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable()
                .and()
                .requestMatcher(request -> false)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(authSecurityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest()
                .authenticated();
    }
}

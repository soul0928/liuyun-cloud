package com.liuyun.web.config;

import com.liuyun.web.interceptor.WebServerProtectInterceptor;
import com.liuyun.web.properties.WebSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/10 14:52
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {WebSecurityProperties.class})
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private WebSecurityProperties webSecurityProperties;

    @Bean
    public HandlerInterceptor handlerInterceptor() {
        return new WebServerProtectInterceptor(webSecurityProperties);
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor())
                .addPathPatterns("/**");
    }

}

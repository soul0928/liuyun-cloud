package com.liuyun.oauth2.config;

import com.liuyun.oauth2.interceptor.AuthServerProtectInterceptor;
import com.liuyun.oauth2.properties.AuthSecurityProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 */
public class AuthInterceptorConfig implements WebMvcConfigurer {

    private final AuthSecurityProperties authSecurityProperties;

    public AuthInterceptorConfig(AuthSecurityProperties authSecurityProperties) {
        this.authSecurityProperties = authSecurityProperties;
    }

    //@Bean
    public HandlerInterceptor authServerProtectInterceptor() {
        return new AuthServerProtectInterceptor(authSecurityProperties);
    }

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authServerProtectInterceptor());
    }
}

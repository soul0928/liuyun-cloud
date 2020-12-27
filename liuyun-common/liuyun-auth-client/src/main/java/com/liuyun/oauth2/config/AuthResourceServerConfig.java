package com.liuyun.oauth2.config;

import com.liuyun.oauth2.handler.AuthAccessDeniedHandler;
import com.liuyun.oauth2.handler.AuthExceptionEntryPoint;
import com.liuyun.oauth2.properties.AuthSecurityProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 00:10
 **/
@EnableResourceServer
public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final AuthSecurityProperties authSecurityProperties;
    private final AuthAccessDeniedHandler authAccessDeniedHandler;
    private final AuthExceptionEntryPoint authExceptionEntryPoint;
    private final OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;
    private final TokenStore tokenStore;

    public AuthResourceServerConfig(AuthSecurityProperties authSecurityProperties, AuthAccessDeniedHandler authAccessDeniedHandler,
                                    AuthExceptionEntryPoint authExceptionEntryPoint, OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler, TokenStore tokenStore) {
        this.authSecurityProperties = authSecurityProperties;
        this.authAccessDeniedHandler = authAccessDeniedHandler;
        this.authExceptionEntryPoint = authExceptionEntryPoint;
        this.oAuth2WebSecurityExpressionHandler = oAuth2WebSecurityExpressionHandler;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .requestMatchers().antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers(authSecurityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore)
                .stateless(true)
                .authenticationEntryPoint(authExceptionEntryPoint)
                .expressionHandler(oAuth2WebSecurityExpressionHandler)
                .accessDeniedHandler(authAccessDeniedHandler);
    }

}

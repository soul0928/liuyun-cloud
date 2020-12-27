package com.liuyun.oauth2.config;

import com.liuyun.oauth2.handler.AuthAccessDeniedHandler;
import com.liuyun.oauth2.handler.AuthExceptionEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 20:39
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class AuthResourceServerConfig2 extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;
    @Resource
    private AuthExceptionEntryPoint authExceptionEntryPoint;
    @Resource
    private AuthAccessDeniedHandler authAccessDeniedHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .httpBasic().disable()
                .requestMatchers().antMatchers("/**")
                /*.exceptionHandling()
                .accessDeniedHandler(authAccessDeniedHandler)
                .authenticationEntryPoint(authExceptionEntryPoint)
                .and()*/
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources/*.tokenStore(tokenStore)
                .stateless(true)*/
                .authenticationEntryPoint(authExceptionEntryPoint)
                // .expressionHandler(oAuth2WebSecurityExpressionHandler)
                .accessDeniedHandler(authAccessDeniedHandler);
    }

    public HttpSecurity setAuthenticate(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl) {
        return authorizedUrl.access("@AuthPermissionService.hasPermission(request, authentication)").and();
    }
}

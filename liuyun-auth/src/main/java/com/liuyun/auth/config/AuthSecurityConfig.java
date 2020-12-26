package com.liuyun.auth.config;

import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.oauth2.constants.Oauth2Constants;
import com.liuyun.oauth2.properties.AuthSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/14 13:20
 **/
@Slf4j
@Configuration
@EnableWebSecurity
@Import({AuthPasswordConfig.class})
@ConfigurationPropertiesScan({"com.liuyun.oauth2.properties"})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthSecurityProperties authSecurityProperties;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


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
     * 全局用户信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(authSecurityProperties.getIgnore().getUrls()).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl(Oauth2Constants.LOGOUT_URL)
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler)
                .clearAuthentication(true)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf().disable()
                // 解决不允许显示在iframe的问题
                .headers().frameOptions().disable().cacheControl();
    }

}

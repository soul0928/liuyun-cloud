package com.liuyun.auth.config;

import com.liuyun.auth.config.handler.AuthOauthHandle;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.auth.service.AuthRedisCodeService;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.redis.constants.AuthRedisConstants;
import com.liuyun.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 17:24
 **/
@Slf4j
@Configuration
@EnableAuthorizationServer
@Import({AuthOauthHandle.class})
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private RedisService redisService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private AuthClientDetailsService authClientDetailsService;
    @Autowired
    private AuthRedisCodeService authRedisCodeService;
    @Autowired
    private WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator;


    /**
     * 配置认证管理器
     *
     * @param endpoints {@link AuthorizationServerEndpointsConfigurer}
     * @author wangdong
     * @date 2020/12/17 3:20 下午
     **/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 配置认证管理器 redis
        endpoints.authenticationManager(authenticationManager)
                // 支持GET  POST  请求获取token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //配置用户服务
                .userDetailsService(authUserDetailsService)
                .authorizationCodeServices(authRedisCodeService)
                .exceptionTranslator(webResponseExceptionTranslator)
                .tokenStore(tokenStore())
        // .reuseRefreshTokens(true)
        ;

    }


    /**
     * 配置应用名称 应用id
     * 配置OAuth2的客户端相关信息
     *
     * @param clients {@link ClientDetailsServiceConfigurer}
     * @author wangdong
     * @date 2020/12/15 9:26 下午
     **/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.withClientDetails(authClientDetailsService);
        clients.inMemory()
                .withClient("admin")
                .secret(passwordEncoder.encode("admin"))
                .scopes("all")
                .authorizedGrantTypes("password","refresh_token","authorization_code")
                .redirectUris("http://www.liuyunm.com");
    }

    /**
     * 创建令牌存储对象
     *
     * @return org.springframework.security.oauth2.provider.token.TokenStore
     * @author wangdong
     * @date 2020/12/15 9:32 下午
     **/
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisService.getRedisConnectionFactory2());
        // 设置redis token存储中的前缀
        String cacheKey = AuthRedisConstants.getKey(AuthRedisConstants.AUTH_PREFIX, AuthRedisConstants.TOKEN_PREFIX);
        redisTokenStore.setPrefix(cacheKey);
        return redisTokenStore;
    }

    /**
     * 对应于配置AuthorizationServer安全认证的相关信息
     * 创建ClientCredentialsTokenEndpointFilter核心过滤器
     *
     * @param security {@link AuthorizationServerSecurityConfigurer}
     * @author wangdong
     * @date 2020/12/15 9:26 下午
     **/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                //不拦截所有获取token的访问
                .tokenKeyAccess("permitAll()")
                //验证token
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    //定义授权和令牌端点和令牌服务
    //刷新令牌时需要的认证管理和用户信息来源


    // 默认处于安全，会把UsernameNotFoundException转为BadCredentialsException，就是 “坏的凭据”，注入下面配置的bean
    /*@Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
        impl.setUserDetailsService(authUserDetailsService);
        impl.setHideUserNotFoundExceptions(false);
        return impl;
    }*/
}

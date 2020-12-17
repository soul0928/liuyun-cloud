package com.liuyun.auth.config;

import com.liuyun.auth.config.handler.AuthResponseExceptionTranslator;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.auth.service.AuthRedisCodeService;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
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
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Autowired
    private AuthClientDetailsService authClientDetailsService;

    @Autowired
    private AuthRedisCodeService authRedisCodeService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthResponseExceptionTranslator authResponseExceptionTranslator;

    /**
     * 配置认证管理器
     *
     * @return void
     * @author wangdong
     * @date 2020/7/22 12:36 下午
     **/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
       // 配置认证管理器 redis
       endpoints.authenticationManager(authenticationManager)
                //配置用户服务
                .userDetailsService(authUserDetailsService)
               .authorizationCodeServices(authRedisCodeService)
               .exceptionTranslator(authResponseExceptionTranslator)
                //配置token存储的服务与位置
                .tokenServices(tokenService())
                .tokenStore(tokenStore());
    }

    /**
     * 扩展 token 规则
     * @author wangdong
     * @date 2020/7/22 12:33 下午
     * @return org.springframework.security.oauth2.provider.token.DefaultTokenServices
     **/
    @Bean
    public DefaultTokenServices tokenService() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 配置token存储
        tokenServices.setTokenStore(tokenStore());
        // 开启支持refresh_token，此处如果之前没有配置，启动服务后再配置重启服务，可能会导致不返回token的问题，解决方式：清除redis对应token存储
        tokenServices.setSupportRefreshToken(true);
        // 复用refresh_token
        tokenServices.setReuseRefreshToken(true);
        // token 有效期，设置12小时
        tokenServices.setAccessTokenValiditySeconds(12 * 60 * 60);
        // refresh_token有效期，设置一周
        tokenServices.setRefreshTokenValiditySeconds(7 * 24 * 60 * 60);
        return tokenServices;
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
        return new RedisTokenStore(redisService.getRedisConnectionFactory());
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
        clients.withClientDetails(authClientDetailsService);
        authClientDetailsService.loadAllClientToCache();
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
        // 获取令牌不需要认证，校验令牌需要认证，允许表单认证
        security.tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                //让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients();
    }
}

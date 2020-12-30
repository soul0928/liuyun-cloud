package com.liuyun.auth.config;

import com.google.common.collect.Lists;
import com.liuyun.auth.config.handler.AuthWebResponseExceptionTranslator;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.auth.service.AuthRedisCodeService;
import com.liuyun.auth.service.AuthUserDetailsService;
import com.liuyun.redis.constants.AuthRedisConstants;
import com.liuyun.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.List;
import java.util.UUID;

/**
 * oauth2 认证服务器配置
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 17:24
 **/
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenEnhancer tokenEnhancer;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private AuthRedisCodeService authRedisCodeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    @Autowired
    private AuthClientDetailsService authClientDetailsService;
    @Autowired
    private AuthWebResponseExceptionTranslator authWebResponseExceptionTranslator;


    /**
     * 配置认证管理器
     *
     * @param endpoints {@link AuthorizationServerEndpointsConfigurer}
     * @author wangdong
     * @date 2020/12/17 3:20 下午
     **/
    @Override
    @SuppressWarnings("unchecked")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = Lists.newArrayListWithCapacity(2);
        tokenEnhancers.add(tokenEnhancer);
        tokenEnhancers.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(authUserDetailsService)
                .authorizationCodeServices(authRedisCodeService)
                .exceptionTranslator(authWebResponseExceptionTranslator)
                // refresh_token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                //      1.重复使用：access_token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2.非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新而无需失效再次登录
                .reuseRefreshTokens(false)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .pathMapping("/oauth/confirm_access", "/auth/confirm_access");
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
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
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
        // 解决每次生成的 token都一样的问题
        redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
        return redisTokenStore;
    }


}

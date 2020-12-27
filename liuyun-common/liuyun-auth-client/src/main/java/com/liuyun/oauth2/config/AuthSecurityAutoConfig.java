package com.liuyun.oauth2.config;

import com.liuyun.oauth2.handler.AuthAccessDeniedHandler;
import com.liuyun.oauth2.handler.AuthExceptionEntryPoint;
import com.liuyun.oauth2.properties.AuthSecurityProperties;
import com.liuyun.redis.constants.AuthRedisConstants;
import com.liuyun.redis.service.RedisService;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.UUID;

/**
 * Security 自动配置
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/27 00:02
 **/
@Configuration
@Import(RedisService.class)
@ConfigurationPropertiesScan({"com.liuyun.oauth2.properties"})
//@ConditionalOnProperty(prefix = "liuyun.security", name = "enable", havingValue = "true", matchIfMissing = true)
public class AuthSecurityAutoConfig {

    private final RedisService redisService;

    private final AuthSecurityProperties authSecurityProperties;

    public AuthSecurityAutoConfig(RedisService redisService, AuthSecurityProperties authSecurityProperties) {
        this.redisService = redisService;
        this.authSecurityProperties = authSecurityProperties;
    }

    @Bean
    public AuthAccessDeniedHandler authAccessDeniedHandler() {
        return new AuthAccessDeniedHandler();
    }

    @Bean
    public AuthExceptionEntryPoint authExceptionEntryPoint() {
        return new AuthExceptionEntryPoint();
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

    @Bean
    public AuthInterceptorConfig authSecurityInterceptorConfigure() {
        return new AuthInterceptorConfig(authSecurityProperties);
    }

   /* @Bean
    public AuthResourceServerConfig authResourceServerConfig() {
        return new AuthResourceServerConfig(authSecurityProperties, this.authAccessDeniedHandler(), authExceptionEntryPoint(),
                tokenStore());
    }*/
}

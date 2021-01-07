package com.liuyun.auth.config;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Map;
import java.util.Objects;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/29 00:07
 **/
@Slf4j
@Configuration
public class AuthJwtTokenStore {

    @Bean
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     *
     * @return java.security.KeyPair
     * @author wangdong
     * @date 2020/12/29 12:18 上午
     **/
    @Bean
    public KeyPair keyPair() {
        KeyProperties.KeyStore keyStore = keyProperties().getKeyStore();
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                keyStore.getLocation(), keyStore.getPassword().toCharArray());
        return factory.getKeyPair(
                keyStore.getAlias(), keyStore.getSecret().toCharArray());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * jwt 生成token 定制化处理
     * 添加一些额外的用户信息到token里面
     *
     * @return org.springframework.security.oauth2.provider.token.TokenEnhancer
     * @author wangdong
     * @date 2020/12/29 12:19 上午
     **/
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = MapUtil.newHashMap(2);
            Authentication userAuthentication = authentication.getUserAuthentication();
            // 客户端授权, 不包含用户信息
            if (Objects.nonNull(userAuthentication)) {
                Object credentials = userAuthentication.getCredentials();
                additionalInfo.put("test", credentials);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            }
            return accessToken;
        };
    }


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}

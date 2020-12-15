package com.liuyun.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 18:57
 **/
@Slf4j
public class AuthKeyStoreConfig {

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    @Bean("keyProp")
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    @Bean
    public KeyPair keyPair() {
        return new KeyStoreKeyFactory
                (keyProperties.getKeyStore().getLocation(), keyProperties.getKeyStore().getSecret().toCharArray())
                .getKeyPair(keyProperties.getKeyStore().getAlias());
    }
}

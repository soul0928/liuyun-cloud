package com.liuyun.auth.service.impl;

import com.liuyun.auth.service.AuthRedisCodeService;
import com.liuyun.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 21:44
 **/
@Slf4j
@Component
public class AuthRedisCodeServiceImpl extends RandomValueAuthorizationCodeServices implements AuthRedisCodeService {

    /**
     * code缓存到redis 过期时间 10分钟
     */
    private static final long TIMEOUT = 10L;

    @Autowired
    private RedisService redisService;

    /**
     * 替换JdbcAuthorizationCodeServices的存储策略
     * 将存储code到redis，并设置过期时间，10分钟
     *
     * @param code {@link String}  随机 CODE 码
     * @param authentication {@link OAuth2Authentication}
     * @author wangdong
     * @date 2020/12/15 9:47 下午
     **/
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        this.redisService.set(getKey(code), authentication, TIMEOUT, TimeUnit.SECONDS);
    }


    @Override
    protected OAuth2Authentication remove(String code) {
        String codeKey = getKey(code);
        OAuth2Authentication token = (OAuth2Authentication) redisService.get(codeKey);
        this.redisService.delete(codeKey);
        return token;
    }

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        return super.createAuthorizationCode(authentication);
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        return super.consumeAuthorizationCode(code);
    }

    /**
     * redis中 code key的前缀
     *
     * @param code {@link String} 随机 CODE 码
     * @return java.lang.String
     * @author wangdong
     * @date 2020/12/15 9:51 下午
     **/
    private String getKey(String code) {
        return "oauth:code:" + code;
    }
}

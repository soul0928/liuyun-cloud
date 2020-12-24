package com.liuyun.auth.service.impl;

import com.liuyun.auth.service.AuthRedisCodeService;
import com.liuyun.redis.constants.AuthRedisConstants;
import com.liuyun.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.Objects;
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
        // 定制 redisTemplate value 序列化方式, 防止反序列化失败
        RedisTemplate<String, Object> redisTemplate = redisService.getRedisTemplate2();
        redisTemplate.opsForValue().set(getKey(code), authentication, TIMEOUT, TimeUnit.MINUTES);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        String codeKey = getKey(code);
        Object obj = redisService.getRedisTemplate2().opsForValue().get(codeKey);
        if (Objects.isNull(obj)) {
            throw new InvalidGrantException("该 authorization_code 不存在 : " + code);
        }
        OAuth2Authentication token = (OAuth2Authentication) obj;
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
        return AuthRedisConstants.getKey(AuthRedisConstants.AUTH_PREFIX, AuthRedisConstants.AUTHORIZATION_CODE_PREFIX, code);
    }
}

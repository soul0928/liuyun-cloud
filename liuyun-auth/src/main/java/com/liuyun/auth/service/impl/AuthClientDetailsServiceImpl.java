package com.liuyun.auth.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.liuyun.auth.config.utils.AuthClientDetailsUtil;
import com.liuyun.auth.mapper.OauthClientDetailsMapper;
import com.liuyun.auth.service.AuthClientDetailsService;
import com.liuyun.model.auth.entity.OauthClientDetailsEntity;
import com.liuyun.redis.constants.AuthRedisConstants;
import com.liuyun.redis.service.RedisService;
import com.liuyun.utils.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/15 14:30
 **/
@Slf4j
@Component
public class AuthClientDetailsServiceImpl implements AuthClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加载 client 数据放入缓存
     *
     * @author wangdong
     * @date 2020/12/22 10:46 上午
     **/
    @PostConstruct
    public void init() {
        loadAllClientToCache(null);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        String cacheKey = AuthRedisConstants.getKey(AuthRedisConstants.AUTH_PREFIX, AuthRedisConstants.CLIENT_INFO_PREFIX);
        RedisTemplate<String, Object> redisTemplate2 = redisService.getRedisTemplate2();
        if (redisService.exists(cacheKey)) {
            Object obj = redisTemplate2.opsForHash().get(cacheKey, clientId);
            if (Objects.isNull(obj)) {
                return null;
            }
            return (ClientDetails) obj;
        }
        String lockKey = AuthRedisConstants.getKey(AuthRedisConstants.AUTH_PREFIX, AuthRedisConstants.LOCK_CLIENT_PREFIX);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean tryLock = lock.tryLock(5, 5, TimeUnit.SECONDS);
            if (tryLock) {
                if (redisService.exists(cacheKey)) {
                    Object obj = redisTemplate2.opsForHash().get(cacheKey, clientId);
                    if (Objects.isNull(obj)) {
                        return null;
                    }
                    return (ClientDetails) obj;
                }
                return loadAllClientToCache(clientId);
            }
            log.info("该客户端不存在 clientId : " + clientId);
            return null;
        } catch (Exception e) {
            log.info("根据 clientId 查询客户端信息, 查询失败 -> [{}]", e.getMessage());
            return null;
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private ClientDetails loadAllClientToCache(String clientId) {
        List<OauthClientDetailsEntity> list = new LambdaQueryChainWrapper<>(this.oauthClientDetailsMapper)
                .eq(OauthClientDetailsEntity::getDelFlag, OauthClientDetailsEntity.DEL_FLAG_NORMAL)
                .list();
        Map<String, Object> map = new HashMap<>(list.size());
        list.forEach(i -> {
            // 加密
            i.setClientSecret(passwordEncoder.encode(i.getClientSecret()));
            map.put(i.getClientId(), AuthClientDetailsUtil.getClientDetails(i));
        });
        String cacheKey = AuthRedisConstants.getKey(AuthRedisConstants.AUTH_PREFIX, AuthRedisConstants.CLIENT_INFO_PREFIX);
        redisService.getRedisTemplate2().opsForHash().putAll(cacheKey, map);
        if (StringUtils.isNotBlank(clientId)) {
            Object obj = map.get(clientId);
            if (Objects.nonNull(obj)) {
                return (ClientDetails) obj;
            }
        }
        return null;
    }

}

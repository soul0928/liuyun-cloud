package com.liuyun.gateway.config.oauth2;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.liuyun.gateway.service.SysRolePermissionFeignService;
import com.liuyun.redis.constants.UserRedisConstants;
import com.liuyun.redis.service.RedisService;
import com.liuyun.utils.global.enums.GlobalResultEnum;
import com.liuyun.utils.lang.StringUtils;
import com.liuyun.utils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权管理器
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/3 13:25
 **/
@Slf4j
@Component
public class ResourceAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private GatewaySecurityProperties gatewaySecurityProperties;
    @Autowired
    private SysRolePermissionFeignService sysRolePermissionFeignService;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String url = request.getURI().getPath();


        // OPTIONS 跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // token为空拒绝访问
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        Set<String> authorities = getRolesByUrl(url);
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(roleCode -> {
                    // roleCode是请求用户的角色(格式:ROLE_roleCode)，authorities是请求资源所需要角色的集合
                    log.info("访问路径 -> [{}], 用户角色信息 -> [{}], 资源需要权限authorities -> [{}]", url, roleCode, authorities);
                    return authorities.contains(roleCode);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private Set<String> getRolesByUrl(String url) {
        String cacheKey = UserRedisConstants.getKey(UserRedisConstants.USER_PREFIX, UserRedisConstants.ROLE_INFO_PREFIX);
        // 请求路径匹配到的资源需要的角色权限集合authorities统计
        Set<String> authorities = new HashSet<>();
        if (redisService.exists(cacheKey)) {
            return getRoles(url, cacheKey, authorities);
        }
        String lockKey = UserRedisConstants.getKey(UserRedisConstants.USER_PREFIX, UserRedisConstants.LOCK_ROLE_PREFIX);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean tryLock = lock.tryLock(5, 5, TimeUnit.SECONDS);
            if (tryLock) {
                // 再次判断缓存中是否存在
                if (redisService.exists(cacheKey)) {
                    return getRoles(url, cacheKey, authorities);
                }
                HashMap<String, String> map = Maps.newHashMap();
                map.put("url", url);
                log.info("获取请求url所需角色编码, 请求Feign接口, 请求参数 -> [{}]", JSONUtil.toJsonStr(map));
                Result<Set<String>> result = this.sysRolePermissionFeignService.getRoleByUrl(map);
                log.info("获取请求url所需角色编码, 请求Feign接口, 响应参数 -> [{}]", JSONUtil.toJsonStr(result));
                if (!Objects.equals(GlobalResultEnum.SUCCESS.getCode(), result.getCode())) {
                    return authorities;
                }
                return result.getResult();
            }
            return authorities;
        } catch (Exception e) {
            log.error("获取请求url所需角色编码,获取锁失败 -> [{}]", e.getMessage());
            e.printStackTrace();
            return authorities;
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private Set<String> getRoles(String url, String cacheKey, Set<String> authorities) {
        PathMatcher matcher = new AntPathMatcher();
        Map<String, Object> map = redisService.hGetAll(cacheKey);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!matcher.match(entry.getKey(), url)) {
                continue;
            }
            authorities.addAll(Convert.toList(String.class, entry.getValue()));
        }
        return authorities;
    }
}

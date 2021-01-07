package com.liuyun.redis.constants;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/24 17:32
 **/
public class UserRedisConstants extends AbstractRedisConstants {

    public final static String USER_PREFIX = "user";

    /**
     * 获取缓存 role 信息 前缀
     */
    public final static String ROLE_INFO_PREFIX = "role:info";

    /**
     * 获取缓存role信息 锁key 前缀
     */
    public final static String LOCK_ROLE_PREFIX = "lock:role";


}

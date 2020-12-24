package com.liuyun.redis.constants;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/24 17:32
 **/
public class AuthRedisConstants extends AbstractRedisConstants {

    public final static String AUTH_PREFIX = "auth";

    /**
     * 获取缓存 client lock 前缀
     */
    public final static String LOCK_CLIENT_PREFIX = "lock:client";

    /**
     * 获取缓存 client 信息 前缀
     */
    public final static String CLIENT_INFO_PREFIX = "client:info";

    /**
     * 获取缓存 client 信息 前缀
     */
    public final static String AUTHORIZATION_CODE_PREFIX = "authorization:code";

    /**
     * 获取缓存 token 信息 前缀
     */
    public final static String TOKEN_PREFIX = "token";


}

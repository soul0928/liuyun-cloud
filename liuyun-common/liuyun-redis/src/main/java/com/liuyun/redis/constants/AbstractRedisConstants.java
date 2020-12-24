package com.liuyun.redis.constants;

import java.util.Objects;

/**
 *
 * RedisCacheKey 常量类
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/24 17:27
 **/
public abstract class AbstractRedisConstants {

    protected final static String GLOBAL_PREFIX = "liuyun";

    protected final static String DELIMITER = ":";

    /**
     * getKey
     *
     * @param module {@link String} 模块
     * @param key    {@link String} key
     * @return java.lang.String key
     * @author wangdong
     * @date 2020/12/24 5:53 下午
     **/
    public static String getKey(String module, String key) {
        return getKey(module, key, null);
    }

    /**
     * hetKey
     *
     * @param module {@link String} 模块
     * @param key {@link String} key
     * @param unique {@link Object} 唯一值
     * @return java.lang.String  key
     * @author wangdong
     * @date 2020/12/24 5:47 下午
     **/
    public static String getKey(String module, String key, Object  unique) {
        String result = GLOBAL_PREFIX + DELIMITER + module + DELIMITER + key;
        return Objects.isNull(unique) ? result : (result + DELIMITER + unique);
    }
}

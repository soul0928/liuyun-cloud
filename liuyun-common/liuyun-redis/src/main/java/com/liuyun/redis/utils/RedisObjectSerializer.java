package com.liuyun.redis.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 定义的序列化操作表示可以序列化所有类的对象
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/22 10:04
 **/
public class RedisObjectSerializer implements RedisSerializer<Object> {

    /**
     * 做一个空数组，不是null
     */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public RedisObjectSerializer() {
        super();
    }

    @Override
    public byte[] serialize(Object obj) {
        // 这个时候没有要序列化的对象出现，所以返回的字节数组应该就是一个空数组
        if (Objects.isNull(obj)) {
            return EMPTY_BYTE_ARRAY;
        }
        // 将对象变为字节数组
        return JSONUtil.toJsonStr(obj).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        // 此时没有对象的内容信息
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
